package com.example.openoff.domain.auth.application.service.apple;

import com.example.openoff.common.config.openfeign.AppleClient;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.auth.application.dto.response.apple.AppleUserInfoResponseDto;
import com.example.openoff.domain.auth.application.exception.AppleOIDCException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleOIDCUserProvider {

    private final AppleJwtParser appleJwtParser;
    private final AppleClient appleClient;
    private final PublicKeyGenerator publicKeyGenerator;
    private final AppleClaimsValidator appleClaimsValidator;

    public AppleUserInfoResponseDto getApplePlatformMember(String identityToken) {
        Map<String, String> headers = appleJwtParser.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();

        PublicKey publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys);

        Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
        validateClaims(claims);
        return new AppleUserInfoResponseDto(claims.getSubject(), claims.get("email", String.class));
    }

    private void validateClaims(Claims claims) {
        if (!appleClaimsValidator.isValid(claims)) {
            throw new AppleOIDCException(Error.APPLE_OIDC_FAILED7);
        }
    }
}
