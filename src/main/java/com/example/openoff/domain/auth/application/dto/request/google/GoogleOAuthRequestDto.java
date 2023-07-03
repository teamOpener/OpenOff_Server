package com.example.openoff.domain.auth.application.dto.request.google;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleOAuthRequestDto {
    private String redirectUri;
    private String clientId;
    private String clientSecret;
    private String code;
    private String responseType;
    private String scope;
    private String accessType;
    private String grantType;
    private String state;
    private String includeGrantedScopes;
    private String loginHint;
    private String prompt;
}
