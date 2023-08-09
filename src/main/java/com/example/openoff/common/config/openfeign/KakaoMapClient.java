package com.example.openoff.common.config.openfeign;

import com.example.openoff.domain.eventInstance.application.dto.response.KakaoAddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "KakaoMapClient",
        url = "https://dapi.kakao.com/v2/local/search/address.json")
public interface KakaoMapClient {
    @GetMapping("")
    KakaoAddressResponse getKakaoAddressToCoord(@RequestParam("query") String query,
                                                @RequestParam("analyze_type") String analyzeType,
                                                @RequestHeader("Authorization") String authorization);
}