package com.example.openoff.domain.auth.application.service.kakao;

import com.example.openoff.common.config.openfeign.KakaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthOidcHelper {

    private final KakaoClient kakaoClient;
    @Value("${oauth.kakao.iss}")
    private String iss;

    private final KakaoOIDCUserProvider kakaoOIDCUserProvider;
    @Value("${oauth.kakao.client-id}")
    private String aud;

    // kid를 토큰에서 가져온다.
    private String getKidFromUnsignedIdToken(String token, String iss, String aud) {
        return kakaoOIDCUserProvider.getKidFromUnsignedTokenHeader(token, iss, aud);
    }

    public OidcDecodePayload getPayloadFromIdToken(String token) {
        String kid = getKidFromUnsignedIdToken(token, iss, aud);
        KakaoPublicKeys kakaoPublicKeys = kakaoClient.getKakaoOIDCOpenKeys();

        // KakaoOauthHelper 에서 공개키를 조회했고 해당 DTO를 넘겨준다.
        KakaoPublicKey kakaoPublicKey =
                kakaoPublicKeys.getKeys().stream()
                        // 같은 kid를 가져온다.
                        .filter(o -> o.getKid().equals(kid))
                        .findFirst()
                        .orElseThrow();
        // 검증이 된 토큰에서 바디를 꺼내온다.
        return kakaoOIDCUserProvider.getOIDCTokenBody(
                token, kakaoPublicKey.getN(), kakaoPublicKey.getE());
    }
}
