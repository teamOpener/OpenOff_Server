package com.example.openoff.domain.auth.application.dto.request.kakao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class KakaoOIDCRequestDto {

    @NotBlank(message = "1012:공백일 수 없습니다.")
    private String token;
}
