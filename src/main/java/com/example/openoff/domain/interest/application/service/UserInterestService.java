package com.example.openoff.domain.interest.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.interest.application.dto.request.AddInterestRequestDto;
import com.example.openoff.domain.interest.domain.service.InterestService;
import com.example.openoff.domain.user.application.dto.response.UserInfoResponseDto;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class UserInterestService {
    private final UserUtils userUtils;
    private final InterestService interestService;

    @Transactional
    public ResponseDto<UserInfoResponseDto> saveInterests(AddInterestRequestDto addInterestRequestDto) {
        User user = userUtils.getUser();
        if (addInterestRequestDto.getInterestTypeList().size() >= 4) { throw UserException.of(Error.TOO_MANY_INTEREST); }
        interestService.saveInterests(user, addInterestRequestDto.getInterestTypeList());
        return ResponseDto.of(HttpStatus.OK.value(), "관심 분야 저장 성공!", UserInfoResponseDto.from(user));
    }
}
