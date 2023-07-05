package com.example.openoff.domain.auth.application.service.google;

import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.auth.application.dto.request.google.GoogleOAuthRequestDto;
import com.example.openoff.domain.auth.application.dto.response.google.GoogleOAuthResponseDto;
import com.example.openoff.domain.auth.application.dto.response.google.GoogleUserInfoResponseDto;
import com.example.openoff.domain.auth.application.exception.GoogleOAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOAuthUserProvider {
    private static final String GRANT_TYPE = "authorization_code";
    private static final String TOKEN_ENDPOINT = "/token";
    private static final String TOKEN_INFO_ENDPOINT = "/tokeninfo";

    private final GoogleOAuthProperties googleOAuthProperties;

    private final RestTemplate restTemplate;


    public String getGoogleAuthUrl(){
        String reqUrl = googleOAuthProperties.getLoginUrl() + "/o/oauth2/v2/auth?client_id=" + googleOAuthProperties.getClientId() + "&redirect_uri=" + googleOAuthProperties.getRedirectUrl()
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }

    public GoogleUserInfoResponseDto getGoogleUserInfo(String code) {
        GoogleOAuthRequestDto googleOAuthRequest = GoogleOAuthRequestDto
                .builder()
                .clientId(googleOAuthProperties.getClientId())
                .clientSecret(googleOAuthProperties.getClientSecret())
                .code(code)
                .redirectUri(googleOAuthProperties.getRedirectUrl())
                .grantType(GRANT_TYPE)
                .build();

        ResponseEntity<GoogleOAuthResponseDto> apiResponse = restTemplate.postForEntity(
                googleOAuthProperties.getAuthUrl() + TOKEN_ENDPOINT,
                googleOAuthRequest,
                GoogleOAuthResponseDto.class);

        if (!apiResponse.getStatusCode().is2xxSuccessful() || apiResponse.getBody() == null) {
            throw new GoogleOAuthException(Error.GOOGLE_OAUTH_FAILED);
        }

        String googleToken = apiResponse.getBody().getId_token();
        String requestUrl = UriComponentsBuilder.fromHttpUrl(googleOAuthProperties.getAuthUrl() + TOKEN_INFO_ENDPOINT)
                .queryParam("id_token", googleToken)
                .toUriString();

        ResponseEntity<GoogleUserInfoResponseDto> userInfoResponse = restTemplate.getForEntity(
                requestUrl,
                GoogleUserInfoResponseDto.class);

        if (!userInfoResponse.getStatusCode().is2xxSuccessful()) {
            throw new GoogleOAuthException(Error.GOOGLE_OAUTH_FAILED2);
        }

        return userInfoResponse.getBody();
    }
}
