package com.example.openoff.domain.interest.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.application.dto.response.InterestInfoResponseDto;
import com.example.openoff.domain.interest.domain.entity.EventInterestField;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.interest.domain.entity.UserInterestField;
import com.example.openoff.domain.interest.domain.repository.EventInterestFieldRepository;
import com.example.openoff.domain.interest.domain.repository.UserInterestFieldRepository;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class FieldService {
    private final UserUtils userUtils;
    private final UserInterestFieldRepository userInterestFieldRepository;
    private final EventInterestFieldRepository eventInterestFieldRepository;

    public ResponseDto<List<InterestInfoResponseDto>> getAllInterestTypeInfo(){
        List<InterestInfoResponseDto> infoResponseDtos = FieldType.getAllInterestTypeInfo().stream()
                .map(InterestInfoResponseDto::from)
                .collect(Collectors.toList());
        return ResponseDto.of(HttpStatus.OK.value(), "관심 분야 리스트", infoResponseDtos);
    }

    public void saveUserInterests(User user, List<FieldType> fieldTypeList) {
        List<UserInterestField> userInterestFields = fieldTypeList.stream()
                .filter(fieldType -> !userInterestFieldRepository.existsByUser_IdAndFieldType(user.getId(), fieldType))
                .map(interest -> UserInterestField.toEntity(user, interest)).collect(Collectors.toList());
        userInterestFieldRepository.saveAllAndFlush(userInterestFields);
    }

    public List<Long> saveEventInterestFields(EventInfo eventInfo, List<FieldType> fieldTypeList) {
        List<EventInterestField> eventInterestFields = fieldTypeList.stream()
                .filter(fieldType -> !eventInterestFieldRepository.existsByEventInfo_IdAndFieldType(eventInfo.getId(), fieldType))
                .map(interest -> EventInterestField.toEntity(eventInfo, interest)).collect(Collectors.toList());
        return eventInterestFieldRepository.saveAll(eventInterestFields).stream().map(EventInterestField::getId).collect(Collectors.toList());
    }
}
