package com.example.openoff.domain.user.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.auth.application.dto.request.sms.NCPSmsInfoRequestDto;
import com.example.openoff.domain.auth.application.dto.response.sms.NCPSmsResponseDto;
import com.example.openoff.domain.auth.application.service.sms.NCPSmsService;
import com.example.openoff.domain.user.application.dto.request.UserOnboardingRequestDto;
import com.example.openoff.domain.user.application.dto.request.UserSmsCheckRequestDto;
import com.example.openoff.domain.user.application.dto.response.UserInfoResponseDto;
import com.example.openoff.domain.user.application.dto.response.UserTotalInfoResponseDto;
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

    @GetMapping("/my/info")
    public ResponseEntity<ResponseDto<UserTotalInfoResponseDto>> getMyTotalInfo() {
        ResponseDto<UserTotalInfoResponseDto> myTotalInfoResponseDto = userQueryService.getMyInfo();
        return ResponseEntity.ok().body(myTotalInfoResponseDto);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> getUserInfo(@RequestParam String uuid) {
        ResponseDto<UserInfoResponseDto> userInfoResponseDto = userQueryService.getUserInfo(uuid);
        return ResponseEntity.ok().body(userInfoResponseDto);
    }

    @PatchMapping("/onboarding")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> updateOnboardingData(@RequestBody UserOnboardingRequestDto userOnboardingRequestDto) {
        ResponseDto<UserInfoResponseDto> userInfoResponseDto = userQueryService.updateOnboardingData(userOnboardingRequestDto);
        return ResponseEntity.ok().body(userInfoResponseDto);
    }

    @PostMapping("/sms")
    public ResponseEntity<ResponseDto<NCPSmsResponseDto>> sendSms(@RequestBody NCPSmsInfoRequestDto ncpSmsInfoRequestDto) {
        ResponseDto<NCPSmsResponseDto> ncpSmsResponseDto = ncpSmsService.sendSms(ncpSmsInfoRequestDto);
        return ResponseEntity.ok().body(ncpSmsResponseDto);
    }

    @PatchMapping("/sms")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> checkSmsNum(@RequestBody UserSmsCheckRequestDto userSmsCheckRequestDto) {
        ResponseDto<UserInfoResponseDto> userInfoResponseDto = userQueryService.checkSmsNum(userSmsCheckRequestDto);
        return ResponseEntity.ok().body(userInfoResponseDto);
    }

}
