package com.example.openoff.domain.ladger.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.eventInstance.application.handler.EventIndexHandler;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.repository.EventApplicantLadgerRepository;
import com.example.openoff.domain.ladger.presentation.SortType;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

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

    public Page<EventInfo> findAllByEventIndexId(Long eventInfoId, FieldType fieldType, String userId, Pageable pageable) {
        return eventApplicantLadgerRepository.findAllApplyInfos(eventInfoId, fieldType, userId, pageable);
    }

    public List<EventApplicantLadger> findMyTotalEventTicketInfos(List<Long> eventInfoIds, String userId) {
        return eventApplicantLadgerRepository.findAllByEventApplicant_IdAndEventInfo_IdIn(userId, eventInfoIds);
    }

    public List<EventApplicantLadger> findMySpecificEventTicketInfos(Long eventInfoId, String userId) {
        return eventApplicantLadgerRepository.findAllByEventApplicant_IdAndEventInfo_Id(userId, eventInfoId);
    }

    public Page<EventApplicantLadger> findAllEventApplicants(Long eventIndexId, String username, LocalDateTime time, String keyword, SortType sort, Pageable pageable) {
        return eventApplicantLadgerRepository.findAllByEventIndex_Id(eventIndexId, username, time, keyword, sort, pageable);
    }

    public EventApplicantLadger getApplicationInfo(Long ladgerId, String userId) {
        return eventApplicantLadgerRepository.findByIdAndEventApplicant_Id(ladgerId, userId)
                .orElseThrow(() -> BusinessException.of(Error.EVENT_APPLICANT_NOT_FOUND));
    }

    public EventApplicantLadger findLadgerInfo(Long ladgerId) {
        return eventApplicantLadgerRepository.findById(ladgerId)
                .orElseThrow(() -> BusinessException.of(Error.EVENT_APPLICANT_NOT_FOUND));
    }

    public EventApplicantLadger findUserIdAndTicketIndexUpdateJoinAt(String userId, String ticketIndex) {
        EventApplicantLadger eventApplicantLadger = eventApplicantLadgerRepository.findByEventApplicant_IdAndTicketIndex(userId, ticketIndex)
                .orElseThrow(() -> BusinessException.of(Error.EVENT_APPLICANT_NOT_FOUND));
        if (!eventApplicantLadger.getIsAccept()) {
            throw BusinessException.of(Error.EVENT_APPLICANT_NOT_ACCEPT);
        }
        eventApplicantLadger.updateIsJoinAndJoinAt(true);
        return eventApplicantLadger;
    }

    public void deleteEventApplicantLadger(Long ladgerId) {
        eventApplicantLadgerRepository.deleteById(ladgerId);
    }

    public List<EventApplicantLadger> findInEventIndex(Long eventIndexId) {
        return eventApplicantLadgerRepository.findApplicantInEventIndex(eventIndexId);
    }

    public List<EventApplicantLadger> findNotAcceptedLadgersByEventIndex(Long eventIndexId) {
        return eventApplicantLadgerRepository.findNotAcceptedApplicantInEventIndex(eventIndexId);
    }

    public List<Long> countEventInfoApplicant(Long eventInfoId) {
        return eventApplicantLadgerRepository.countEventInfoApplicant(eventInfoId);
    }

    public Long countLadgerInEventIndex(Long eventIndexId) {
        return eventApplicantLadgerRepository.countApplicantInEventIndex(eventIndexId);
    }
}
