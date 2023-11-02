package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventInfoRepositoryCustom {
    List<EventInfo> searchAllEventInfosInMap(EventSearchRequestDto eventSearchRequestDto);
    Page<EventInfo> findMainTapEventInfoByFieldType2(FieldType fieldType, Long eventInfoId, Pageable pageable);
    Page<EventInfo> findMainTapEventInfoByVogue2(Long eventInfoId, Integer count, Pageable pageable);

    Page<EventInfo> findHostEventInfo(String userId, Long eventInfoId, FieldType fieldType, Pageable pageable);
    List<EventInfo> findEventInfosByFieldTypes(List<FieldType> fieldTypes);

    List<Long> findOpenEventInfoIds();
}
