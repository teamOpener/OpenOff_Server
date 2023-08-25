package com.example.openoff.common.infrastructure.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.Normalizer;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.img_bucket}")
    private String bucket;

    public List<String> uploadImgList(List<MultipartFile> imgList) {

        if(Objects.isNull(imgList)) return null;
        if(imgList.isEmpty()) return null;
        List<String> uploadUrl = new ArrayList<>();
        for (MultipartFile img : imgList) {
            uploadUrl.add(uploadImg(img));
        }
        return uploadUrl;
    }


    //단일 이미지 업로드
    public String uploadImg(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String originFileName = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFC);
        String fileName = createFileName(originFileName);
        ObjectMetadata objectMetadata = createObjectMetadata(file);

        try (ByteArrayOutputStream baos = resizeImage(file)) {
            uploadToS3(fileName, baos, objectMetadata);
        } catch (IOException e) {
            throw BusinessException.of(Error.FILE_UPLOAD_ERROR);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public List<String> uploadImgs(List<MultipartFile> files) {
        if (Objects.isNull(files) || files.isEmpty()) return Collections.emptyList();

        List<String> uploadedImgUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                return null;
            }

            String originFileName = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFC);
            String fileName = createFileName(originFileName);
            ObjectMetadata objectMetadata = createObjectMetadata(file);

            try (ByteArrayOutputStream baos = resizeImage(file)) {
                uploadToS3(fileName, baos, objectMetadata);
            } catch (IOException e) {
                throw BusinessException.of(Error.FILE_UPLOAD_ERROR);
            }
            String uploadedUrl = amazonS3.getUrl(bucket, fileName).toString();
            uploadedImgUrls.add(uploadedUrl);
        }

        return uploadedImgUrls;
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }

    private ByteArrayOutputStream resizeImage(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(multipartFileToFile(file));
        BufferedImage resizedImage = Thumbnails.of(originalImage)
                .size(320, 396)
                .asBufferedImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", baos);
        return baos;
    }

    private void uploadToS3(String fileName, ByteArrayOutputStream baos, ObjectMetadata metadata) {
        metadata.setContentLength(baos.size());
        try (InputStream inputStream = new ByteArrayInputStream(baos.toByteArray())) {
            PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);
        } catch (Exception e) {
            log.info("S3 업로드 실패 : {}", e.getMessage());
            throw BusinessException.of(Error.FILE_UPLOAD_ERROR);
        }
    }

    //파일명 난수화
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    //파일 확장자 체크
    private String getFileExtension(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf('.'));
        if (!ext.equals(".jpg") && !ext.equals(".png") && !ext.equals(".jpeg") && !ext.equals(".svg+xml") && !ext.equals(".svg") && !ext.equals(".webp")) {
            throw BusinessException.of(Error.FILE_EXTENTION_ERROR);
        }
        return ext;
    }

    public String uploadQRImage(ByteArrayOutputStream out, ByteArrayInputStream inputStream, String ticketIndex) {
        // S3에 업로드할 Object 메타데이터 설정
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(out.size());
        meta.setContentType("image/png");

        // S3에 업로드
        try{
            PutObjectRequest request = new PutObjectRequest(bucket, ticketIndex, inputStream, meta)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);
        } catch (Exception e) {
            throw BusinessException.of(Error.FILE_UPLOAD_ERROR);
        }
        return amazonS3.getUrl(bucket, ticketIndex).toString();
    }

    private File multipartFileToFile(MultipartFile file) {
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (IOException e) {
            throw BusinessException.of(Error.FILE_UPLOAD_ERROR);
        }
    }
}
