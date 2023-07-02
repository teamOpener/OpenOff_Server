package com.example.openoff.common.config.openfeign;

import com.example.openoff.domain.auth.application.service.kakao.KakaoPublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "KakaoAuthClient",
        url = "https://kauth.kakao.com")
public interface KakaoClient {
//    @Cacheable(cacheNames = "KakaoOIDC", cacheManager = "kakaoOidcCacheManager")
    @GetMapping("/.well-known/jwks.json")
    KakaoPublicKeys getKakaoOIDCOpenKeys();
}