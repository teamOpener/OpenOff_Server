package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.entity.EventExtraQuestion;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.service.EventExtraAnswerService;
import com.example.openoff.domain.eventInstance.domain.service.EventExtraQuestionService;
import com.example.openoff.domain.eventInstance.domain.service.EventIndexService;
import com.example.openoff.domain.ladger.application.dto.request.ApplyEventRequestDto;
import com.example.openoff.domain.ladger.domain.service.EventApplicantLadgerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class LadgerCreateUseCase {
    private final UserUtils userUtils;
    private final EventIndexService eventIndexService;
    private final EventApplicantLadgerService eventApplicantLadgerService;
    private final EventExtraQuestionService eventExtraQuestionService;
    private final EventExtraAnswerService eventExtraAnswerService;

    public void createEventApplicationLadger(ApplyEventRequestDto applyEventRequestDto) {
        EventIndex eventIndex = eventIndexService.findById(applyEventRequestDto.getEventIndexId());
        eventApplicantLadgerService.createEventApplicantLadger(eventIndex, userUtils.getUser());

        List<ApplyEventRequestDto.AnswerInfo> answerInfoList = applyEventRequestDto.getAnswerInfoList();

        List<EventExtraQuestion> extraQuestions = eventExtraQuestionService.findExtraQuestions(
                answerInfoList.stream().map(ApplyEventRequestDto.AnswerInfo::getEventExtraQuestionId
                ).collect(Collectors.toList()));

        eventExtraAnswerService.createEventExtraAnswers(applyEventRequestDto, userUtils.getUser(), extraQuestions);
    }
}
