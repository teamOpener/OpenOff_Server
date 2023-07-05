package com.example.openoff.common.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private final String secret;
    private final long accessTokenPeriod;
    private final long refreshTokenPeriod;
}
