package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.config.openfeign.KakaoMapClient;
import com.example.openoff.domain.eventInstance.application.dto.response.KakaoAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLocalService {
    @Value("${oauth.kakao.api-key}")
    private String API_KEY;

    private final KakaoMapClient kakaoMapClient;

    public KakaoAddressResponse getKakaoAddressToCoord(String query, String analyzeType) {
        log.info("query: {}, analyzeType: {}", query, analyzeType);
        return kakaoMapClient.getKakaoAddressToCoord(query, analyzeType, "KakaoAK " + API_KEY, "application/json; charset=utf-8");
    }
}
