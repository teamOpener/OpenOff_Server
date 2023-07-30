package com.example.openoff.domain.ladger.domain.repository;

import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.presentation.SortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface EventApplicantLadgerRepositoryCustom {
    Page<EventInfo> findAllApplyInfos(Long eventInfoId, FieldType fieldType, String userId, Pageable pageable);
    Page<EventApplicantLadger> findAllByEventIndex_Id(Long eventIndexId, String username, LocalDateTime time, String keyword, SortType sort, Pageable pageable);

}
