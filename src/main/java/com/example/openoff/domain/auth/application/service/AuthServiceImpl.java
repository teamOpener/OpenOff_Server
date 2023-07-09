package com.example.openoff.domain.auth.application.service;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.security.jwt.JwtProvider;
import com.example.openoff.domain.auth.application.dto.request.SocialSignupRequestDto;
import com.example.openoff.domain.auth.application.dto.request.apple.AppleOIDCRequestDto;
import com.example.openoff.domain.auth.application.dto.request.google.GoogleOAuthCodeRequestDto;
import com.example.openoff.domain.auth.application.dto.request.kakao.KakaoOIDCRequestDto;
import com.example.openoff.domain.auth.application.dto.request.normal.NormalSignInRequestDto;
import com.example.openoff.domain.auth.application.dto.response.apple.AppleUserInfoResponseDto;
import com.example.openoff.domain.auth.application.dto.response.google.GoogleUserInfoResponseDto;
import com.example.openoff.domain.auth.application.dto.response.kakao.KakaoUserInfoResponseDto;
import com.example.openoff.domain.auth.application.dto.response.normal.CheckEmailRequestDto;
import com.example.openoff.domain.auth.application.dto.response.token.TokenResponseDto;
import com.example.openoff.domain.auth.application.exception.OAuthException;
import com.example.openoff.domain.auth.application.service.apple.AppleOIDCUserProvider;
import com.example.openoff.domain.auth.application.service.google.GoogleOAuthUserProvider;
import com.example.openoff.domain.auth.application.service.kakao.KakaoOIDCUserProvider;
import com.example.openoff.domain.auth.domain.entity.AccountType;
import com.example.openoff.domain.auth.domain.entity.SocialAccount;
import com.example.openoff.domain.auth.domain.service.SocialAccountService;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final GoogleOAuthUserProvider googleOAuthUserProvider;
    private final KakaoOIDCUserProvider kakaoOIDCUserProvider;
    private final AppleOIDCUserProvider appleOIDCUserProvider;
    private final SocialAccountService socialAccountService;
    private final UserQueryService userQueryService;
    private final JwtProvider jwtProvider;
    @Override
    @Transactional
    public ResponseDto<TokenResponseDto> initSocialSignIn(SocialSignupRequestDto socialSignupRequestDto, String socialType) {
        // provider를 보고 어떤 소셜 로그인인지 판단하고 정보 가져옴
        // 가져온 정보로 socialAccount save (이미 있는지 확인)
        SocialAccount socialAccount = null;
        switch (socialType) {
            case "google":
                GoogleUserInfoResponseDto googleUserInfoResponseDto = getGoogleUserInfoByAuthCode(new GoogleOAuthCodeRequestDto(socialSignupRequestDto.getToken()));
                socialAccount = socialAccountService.checkAndSaveSocialAccount(
                        AccountType.GOOGLE,
                        UUID.randomUUID().toString(), // google id로 변경해야함
                        googleUserInfoResponseDto.getEmail(),
                        googleUserInfoResponseDto.getName());
                break;
            case "kakao":
                KakaoUserInfoResponseDto kakaoUserInfoResponseDto = getKakaoUserInfoByIdToken(new KakaoOIDCRequestDto(socialSignupRequestDto.getToken()));
                socialAccount = socialAccountService.checkAndSaveSocialAccount(
                        AccountType.KAKAO,
                        kakaoUserInfoResponseDto.getSub(),
                        kakaoUserInfoResponseDto.getEmail(),
                        kakaoUserInfoResponseDto.getNickname());
                break;
            case "apple":
                AppleUserInfoResponseDto appleUserInfoResponseDto = getAppleUserInfoByIdToken(new AppleOIDCRequestDto(socialSignupRequestDto.getToken()));
                socialAccount = socialAccountService.checkAndSaveSocialAccount(
                        AccountType.APPLE,
                        appleUserInfoResponseDto.getPlatformId(),
                        appleUserInfoResponseDto.getEmail(),
                        UUID.randomUUID().toString()); // name으로 변경해야함
                break;
            default:
                throw new OAuthException(Error.OAUTH_FAILED);
        }
        // User saveOrFind
        User user = userQueryService.initUserSaveOrFind(socialAccount, socialType);

        // JWT 생성부분(rt까지 만료된 상황에 호출될 것이므로 싹다 새로 발급)
        String accessToken = jwtProvider.generateAccessToken(user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());
        // token 발급
        return ResponseDto.of(HttpStatus.OK.value(), "토큰 발행 성공!!" ,TokenResponseDto.of(accessToken,refreshToken));
    }

    @Override
    public GoogleUserInfoResponseDto getGoogleUserInfoByAuthCode(GoogleOAuthCodeRequestDto googleOAuthCodeRequestDto) {
        return googleOAuthUserProvider.getGoogleUserInfo(googleOAuthCodeRequestDto.getCode());
    }

    @Override
    public KakaoUserInfoResponseDto getKakaoUserInfoByIdToken(KakaoOIDCRequestDto kakaoOIDCRequestDto) {
        return kakaoOIDCUserProvider.getPayloadFromIdToken(kakaoOIDCRequestDto.getToken());
    }

    @Override
    public AppleUserInfoResponseDto getAppleUserInfoByIdToken(AppleOIDCRequestDto appleOIDCRequestDto) {
        return appleOIDCUserProvider.getApplePlatformMember(appleOIDCRequestDto.getToken());
    }

    @Override
    public ResponseDto<CheckEmailRequestDto> checkExistEmail(String email) {
        return socialAccountService.checkExistEmailInNormal(email) ?
                ResponseDto.of(HttpStatus.OK.value(), "이미 존재하는 이메일입니다.", CheckEmailRequestDto.builder().check(false).build()) :
                ResponseDto.of(HttpStatus.OK.value(), "가입 가능한 이메일입니다.", CheckEmailRequestDto.builder().check(true).build())
                ;
    }

    @Override
    public ResponseDto<TokenResponseDto> initNormalSignUp(NormalSignInRequestDto normalSignupRequestDto) {
        SocialAccount socialAccount = socialAccountService.checkAndSaveNormalAccount(normalSignupRequestDto.getPassword(), normalSignupRequestDto.getEmail());
        User user = userQueryService.initUserSaveOrFind(socialAccount, "normal");

        String accessToken = jwtProvider.generateAccessToken(user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());
        return ResponseDto.of(HttpStatus.OK.value(), "토큰 발행 성공!!" ,TokenResponseDto.of(accessToken,refreshToken));
    }

    @Override
    public ResponseDto<TokenResponseDto> normalLogin(NormalSignInRequestDto normalSignupRequestDto) {
        SocialAccount normalAccount = socialAccountService.findNormalAccount(normalSignupRequestDto.getPassword(), normalSignupRequestDto.getEmail());
        User user = userQueryService.initUserSaveOrFind(normalAccount, "normal");
        // JWT 생성부분(rt까지 만료된 상황에 호출될 것이므로 싹다 새로 발급)
        String accessToken = jwtProvider.generateAccessToken(user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());
        // token 발급
        return ResponseDto.of(HttpStatus.OK.value(), "일반 로그인 성공!!" ,TokenResponseDto.of(accessToken,refreshToken));
    }
}
