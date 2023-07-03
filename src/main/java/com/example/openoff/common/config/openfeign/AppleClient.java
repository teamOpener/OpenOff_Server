package com.example.openoff.common.config.openfeign;

import com.example.openoff.domain.auth.application.service.apple.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-public-key-client", url = "https://appleid.apple.com/auth")
public interface AppleClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKeys();
}
