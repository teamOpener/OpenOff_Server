package com.example.openoff.common.infrastructure.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3DeleteService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.img_bucket}")
    private String bucket;

    public void deleteImgList(List<String> imgUrlList) {
        if (!imgUrlList.isEmpty()) {
            for (String imgUrl : imgUrlList) {
                deleteImg(imgUrl);
            }
        }
    }

    /*
    개별 이미지 삭제
    */
    public void deleteImg(String originImgUrl) {
        if (originImgUrl == null) return;
        try {
            amazonS3.deleteObject(bucket, originImgUrl.split("/")[3]);
        } catch (AmazonServiceException e) {
            throw BusinessException.of(Error.FILE_DELETE_ERROR);
        }
    }
}
