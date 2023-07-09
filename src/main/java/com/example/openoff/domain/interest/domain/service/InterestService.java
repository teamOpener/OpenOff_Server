package com.example.openoff.domain.interest.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.interest.application.dto.response.InterestInfoResponseDto;
import com.example.openoff.domain.interest.domain.entity.Interest;
import com.example.openoff.domain.interest.domain.entity.InterestType;
import com.example.openoff.domain.interest.domain.repository.UserInterestRepository;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class InterestService {
    private final UserInterestRepository userInterestRepository;

    public ResponseDto<List<InterestInfoResponseDto>> getAllInterestTypeInfo(){
        List<InterestInfoResponseDto> infoResponseDtos = InterestType.getAllInterestTypeInfo().stream()
                .map(InterestInfoResponseDto::from)
                .collect(Collectors.toList());
        return ResponseDto.of(HttpStatus.OK.value(), "관심 분야 리스트", infoResponseDtos);
    }

    public void saveInterests(User user, List<InterestType> interestTypeList) {
        List<Interest> interests = interestTypeList.stream()
                .map(interest -> Interest.toEntity(user, interest)).collect(Collectors.toList());
        userInterestRepository.saveAllAndFlush(interests);
    }
}
