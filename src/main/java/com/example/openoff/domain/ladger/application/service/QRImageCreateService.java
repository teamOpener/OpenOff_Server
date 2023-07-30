package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.infrastructure.s3.S3UploadService;
import com.example.openoff.common.util.EncryptionUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class QRImageCreateService {
    private final S3UploadService s3UploadService;
    private final EncryptionUtils encryptionUtils;
    public String createQRImageAndUploadToS3(String userId, String ticketIndex) {
        int width = 200;
        int height = 200;
        String text = userId + '.' + ticketIndex;
        try {
            String encrypt = encryptionUtils.qrContentEncrypt(text);
            // QR Code 생성
            BitMatrix encode = new MultiFormatWriter().encode(encrypt, BarcodeFormat.QR_CODE, width, height);

            // QR Code를 Image로 변환
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(encode, "PNG", out);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
            return s3UploadService.uploadQRImage(out, inputStream, ticketIndex);
        }catch (Exception e) {
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
        }
    }
}
