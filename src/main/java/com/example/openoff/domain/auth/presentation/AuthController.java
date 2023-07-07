package com.example.openoff.domain.auth.presentation;

import com.example.openoff.domain.auth.application.dto.request.SocialSignupRequestDto;
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

    @PostMapping("/signup/social/{socialType}")
    public ResponseEntity<?> signupSocial(@PathVariable String socialType, @RequestBody SocialSignupRequestDto socialSignupRequestDto)
    {
        TokenResponseDto tokenResponseDto = authService.initSocialSignIn(socialSignupRequestDto, socialType);
        return ResponseEntity.ok().body(tokenResponseDto);
    }
}
