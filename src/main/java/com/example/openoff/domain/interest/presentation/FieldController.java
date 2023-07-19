package com.example.openoff.domain.interest.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.interest.application.dto.request.AddInterestRequestDto;
import com.example.openoff.domain.interest.application.dto.response.InterestInfoResponseDto;
import com.example.openoff.domain.interest.application.service.UserInterestService;
import com.example.openoff.domain.interest.domain.service.FieldService;
import com.example.openoff.domain.user.application.dto.response.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/interest")
@RequiredArgsConstructor
public class FieldController {
    private final FieldService fieldService;
    private final UserInterestService userInterestService;

    @GetMapping(value = "/info")
    public ResponseEntity<ResponseDto<List<InterestInfoResponseDto>>> saveUserInterests() {
        ResponseDto<List<InterestInfoResponseDto>> allInterestTypeInfo = fieldService.getAllInterestTypeInfo();
        return ResponseEntity.ok().body(allInterestTypeInfo);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> saveUserInterests(@RequestBody AddInterestRequestDto addInterestRequestDto) {
        ResponseDto<UserInfoResponseDto> userInfoResponseDtoResponseDto = userInterestService.saveInterests(addInterestRequestDto);
        return ResponseEntity.ok().body(userInfoResponseDtoResponseDto);
    }

}
