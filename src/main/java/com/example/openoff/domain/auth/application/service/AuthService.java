package com.example.openoff.domain.auth.application.service;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.auth.application.dto.request.SocialSignupRequestDto;
import com.example.openoff.domain.auth.application.dto.request.apple.AppleOIDCRequestDto;
import com.example.openoff.domain.auth.application.dto.request.google.GoogleOAuthCodeRequestDto;
import com.example.openoff.domain.auth.application.dto.request.kakao.KakaoOIDCRequestDto;
import com.example.openoff.domain.auth.application.dto.response.apple.AppleUserInfoResponseDto;
import com.example.openoff.domain.auth.application.dto.response.google.GoogleUserInfoResponseDto;
import com.example.openoff.domain.auth.application.dto.response.kakao.KakaoUserInfoResponseDto;
import com.example.openoff.domain.auth.application.dto.response.token.TokenResponseDto;

public interface AuthService {
    // Sign In
    ResponseDto<TokenResponseDto> initSocialSignIn(SocialSignupRequestDto socialSignupRequestDto, String provider);

    // GOOGLE
    GoogleUserInfoResponseDto getGoogleUserInfoByAuthCode(GoogleOAuthCodeRequestDto googleOAuthCodeRequestDto);

    // KAKAO
    KakaoUserInfoResponseDto getKakaoUserInfoByIdToken(KakaoOIDCRequestDto kakaoOIDCRequestDto);

    // APPLE
    AppleUserInfoResponseDto getAppleUserInfoByIdToken(AppleOIDCRequestDto appleOIDCRequestDto);

    // NORMAL
}
