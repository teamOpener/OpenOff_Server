package com.example.openoff.domain.user.presentation;

import com.example.openoff.domain.auth.application.dto.request.sms.NCPSmsInfoRequestDto;
import com.example.openoff.domain.auth.application.dto.response.sms.NCPSmsResponseDto;
import com.example.openoff.domain.auth.application.service.sms.NCPSmsService;
import com.example.openoff.domain.user.application.dto.request.UserOnboardingRequestDto;
import com.example.openoff.domain.user.domain.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserQueryService userQueryService;
    private final NCPSmsService ncpSmsService;

    @PatchMapping("/onboarding")
    public ResponseEntity<?> updateOnboardingData(@RequestBody UserOnboardingRequestDto userOnboardingRequestDto) {
        userQueryService.updateOnboardingData(userOnboardingRequestDto);
        return ResponseEntity.ok().body("onboarding success");
    }

    @PostMapping("/sms")
    public ResponseEntity<?> sendSms(@RequestBody NCPSmsInfoRequestDto ncpSmsInfoRequestDto) {
        NCPSmsResponseDto ncpSmsResponseDto = ncpSmsService.sendSms(ncpSmsInfoRequestDto);
        return ResponseEntity.ok().body(ncpSmsResponseDto);
    }
}
