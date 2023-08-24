package com.example.openoff.domain;

import com.example.openoff.common.config.web.BannerUrlProperties;
import com.example.openoff.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HealthCheckController {
    private final BannerUrlProperties bannerUrlProperties;
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("prod!!");
    }

    @GetMapping("/banner")
    public ResponseEntity<ResponseDto<List<String>>> banner() {
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "배너 이미지 리스트 조회", bannerUrlProperties.getUrls()));
    }
}
