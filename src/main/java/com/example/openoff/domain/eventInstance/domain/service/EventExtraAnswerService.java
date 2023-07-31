package com.example.openoff.domain.eventInstance.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.eventInstance.domain.entity.EventExtraAnswer;
import com.example.openoff.domain.eventInstance.domain.entity.EventExtraQuestion;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.repository.EventExtraAnswerRepository;
import com.example.openoff.domain.ladger.application.dto.request.ApplyEventRequestDto;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventExtraAnswerService {
    private final EventIndexService eventIndexService;
    private final EventExtraAnswerRepository eventExtraAnswerRepository;

    public void createEventExtraAnswers(ApplyEventRequestDto applyEventRequestDto, User answerer, List<EventExtraQuestion> extraQuestions) {
        EventIndex eventIndex = eventIndexService.findById(applyEventRequestDto.getEventIndexId());
        List<EventExtraAnswer> extraAnswers = applyEventRequestDto.getAnswerInfoList().stream()
                .map(answerInfo -> {
                    EventExtraQuestion eventExtraQuestion = extraQuestions.stream()
                            .filter(extraQuestion -> extraQuestion.getId().equals(answerInfo.getEventExtraQuestionId()))
                            .findFirst().orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
                    return EventExtraAnswer.toEntity(answerInfo.getAnswer(), answerer, eventIndex, eventExtraQuestion);
                }).collect(Collectors.toList());
        eventExtraAnswerRepository.saveAll(extraAnswers);
    }
}
