package com.example.openoff.domain.ladger.domain.repository;

import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.presentation.SortType;
import com.example.openoff.domain.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EventApplicantLadgerRepositoryCustom {
    Page<EventInfo> findAllApplyInfos(Long eventInfoId, FieldType fieldType, String userId, Pageable pageable);
    Page<EventApplicantLadger> findAllByEventIndex_Id(Long eventIndexId, Long ladgerId, String username, LocalDateTime time, String keyword, SortType sort, Pageable pageable);

    List<EventApplicantLadger> findApplicantInEventIndex(Long eventIndexId);
    Long countApplicantInEventIndex(Long eventIndexId);
    List<EventApplicantLadger> findNotAcceptedApplicantInEventIndex(Long eventIndexId);
    List<User> findAcceptedApplicantInEventIndex(Long eventIndexId);
    Map<Long, Long> countEventInfoApprovedApplicant(Long eventInfoId);
    Long countEventInfoNotApprovedApplicantByEventIndexId(Long eventIndexId);
}
