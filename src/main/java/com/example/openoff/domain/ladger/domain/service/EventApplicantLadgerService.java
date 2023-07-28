package com.example.openoff.domain.ladger.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.eventInstance.application.handler.EventIndexHandler;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.repository.EventApplicantLadgerRepository;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventApplicantLadgerService {
    private final EventIndexHandler eventIndexHandler;

    private final EventApplicantLadgerRepository eventApplicantLadgerRepository;

    public void createEventApplicantLadger(EventIndex eventIndex, User eventApplicant) {
        EventInfo eventInfo = eventIndex.getEventInfo();
        long count = eventApplicantLadgerRepository.countByEventIndex_IdAndIsAcceptTrue(eventIndex.getId());
        if ( count >= eventInfo.getEventMaxPeople()) {
            eventIndexHandler.eventInfoApplyPermitHandler(eventIndex);
            throw BusinessException.of(Error.EVENT_APPLICANT_FULL);
        }
        eventApplicantLadgerRepository.save(EventApplicantLadger.toEntity(eventIndex, eventApplicant));
        eventInfo.updateTotalRegisterCount();
    }
}
