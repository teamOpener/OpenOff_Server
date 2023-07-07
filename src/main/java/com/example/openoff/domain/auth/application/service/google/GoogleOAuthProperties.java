package com.example.openoff.domain.auth.application.service.google;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "google")
public class GoogleOAuthProperties {
    private final String authUrl;

    private final String loginUrl;

    private final String redirectUrl;

    private final String clientId;

    private final String clientSecret;
}
