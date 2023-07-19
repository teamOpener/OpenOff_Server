package com.example.openoff.domain.user.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.common.infrastructure.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/user/image")
@RequiredArgsConstructor
public class UserImageController {
    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto<Map<String, String>>> getMyTotalInfo(@RequestPart MultipartFile multipartFile) {
        String uploadedImgUrl = s3UploadService.uploadImg(multipartFile);
        ResponseDto<Map<String, String>> responseDto = ResponseDto.of(HttpStatus.OK.value(), "이미지 업로드 성공", Map.of("imgUrl", uploadedImgUrl));
        return ResponseEntity.ok().body(responseDto);
    }
}
