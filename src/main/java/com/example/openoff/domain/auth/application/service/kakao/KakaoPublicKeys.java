package com.example.openoff.domain.auth.application.service.kakao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KakaoPublicKeys {

    private List<KakaoPublicKey> keys;

}
