package com.example.openoff.domain.auth.application.service.google;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "google")
public class GoogleOAuthProperties {
    private String authUrl;

    private String loginUrl;

    private String redirectUrl;

    private String clientId;

    private String clientSecret;
}
