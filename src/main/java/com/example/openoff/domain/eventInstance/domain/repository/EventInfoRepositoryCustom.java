package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.MainTapEventInfoResponse;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventInfoRepositoryCustom {
    List<EventInfo> searchAllEventInfosInMap(EventSearchRequestDto eventSearchRequestDto);
    PageResponse<MainTapEventInfoResponse> findMainTapEventInfoByFieldType(FieldType fieldType, Long eventInfoId, Pageable pageable);
    PageResponse<MainTapEventInfoResponse> findMainTapEventInfoByVogue(Long eventInfoId, Integer count, Pageable pageable);

    List<EventInfo> findHostEventInfo(String userId, Long eventInfoId, Pageable pageable);

}
