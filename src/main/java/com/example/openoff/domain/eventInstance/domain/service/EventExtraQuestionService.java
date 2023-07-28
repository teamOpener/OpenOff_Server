package com.example.openoff.domain.eventInstance.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.domain.eventInstance.domain.entity.EventExtraQuestion;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventExtraQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventExtraQuestionService {
    private final EventExtraQuestionRepository eventExtraQuestionRepository;

    public List<Long> saveEventExtraQuestion(EventInfo eventInfo, List<String> extraQuestionList) {
        List<EventExtraQuestion> extraQuestions = extraQuestionList.stream()
                .map(extraQuestion -> EventExtraQuestion.toEntity(extraQuestion, eventInfo))
                .collect(Collectors.toList());
        return eventExtraQuestionRepository.saveAll(extraQuestions).stream().map(EventExtraQuestion::getId).collect(Collectors.toList());
    }

    public List<EventExtraQuestion> findExtraQuestions(List<Long> extraQuestionIds) {
        return eventExtraQuestionRepository.findAllByIdIn(extraQuestionIds);
    }
}
