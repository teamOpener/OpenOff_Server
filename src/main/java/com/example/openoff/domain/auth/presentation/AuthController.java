package com.example.openoff.domain.auth.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.auth.application.dto.request.SocialSignupRequestDto;
import com.example.openoff.domain.auth.application.dto.request.normal.NormalSignInRequestDto;
import com.example.openoff.domain.auth.application.dto.request.normal.ResetPasswordRequestDto;
import com.example.openoff.domain.auth.application.dto.response.normal.CheckEmailResponseDto;
import com.example.openoff.domain.auth.application.dto.response.normal.SearchIdResponseDto;
import com.example.openoff.domain.auth.application.dto.response.token.TokenResponseDto;
import com.example.openoff.domain.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<TokenResponseDto>> tokenRefresh(@RequestBody TokenResponseDto tokenResponseDto)
    {
        ResponseDto<TokenResponseDto> responseDto = authService.tokenRefresh(tokenResponseDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/signup/social/{socialType}")
    public ResponseEntity<ResponseDto<TokenResponseDto>> signupSocial(@PathVariable String socialType, @RequestBody SocialSignupRequestDto socialSignupRequestDto)
    {
        ResponseDto<TokenResponseDto> tokenResponseDto = authService.initSocialSignIn(socialSignupRequestDto, socialType);
        return ResponseEntity.ok().body(tokenResponseDto);
    }

    @PostMapping("/signup/normal")
    public ResponseEntity<ResponseDto<TokenResponseDto>> signupNormal(@RequestBody NormalSignInRequestDto normalSignupRequestDto) {
        ResponseDto<TokenResponseDto> tokenResponseDto = authService.initNormalSignUp(normalSignupRequestDto);
        return ResponseEntity.ok().body(tokenResponseDto);
    }

    @PostMapping("/login/normal")
    public ResponseEntity<ResponseDto<TokenResponseDto>> loginNormal(@RequestBody NormalSignInRequestDto normalSignupRequestDto) {
        ResponseDto<TokenResponseDto> tokenResponseDto = authService.normalLogin(normalSignupRequestDto);
        return ResponseEntity.ok().body(tokenResponseDto);
    }

    @GetMapping("/check/email")
    public ResponseEntity<ResponseDto<CheckEmailResponseDto>> checkExistEmail(@RequestParam String email) {
        ResponseDto<CheckEmailResponseDto> responseDto = authService.checkExistEmail(email);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/search/id")
    public ResponseEntity<ResponseDto<SearchIdResponseDto>> findIdByPhoneNumber(@RequestParam String phoneNum) {
        ResponseDto<SearchIdResponseDto> searchIdResponseDtoResponseDto = authService.searchIdByPhoneNum(phoneNum);
        return ResponseEntity.ok().body(searchIdResponseDtoResponseDto);
    }

    @PatchMapping("/reset/password")
    public ResponseEntity<ResponseDto<Void>> resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        ResponseDto<Void> responseDto = authService.resetPassword(resetPasswordRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

}
