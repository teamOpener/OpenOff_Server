package com.example.openoff.domain.eventInstance.domain.repository;

import java.util.List;
import java.util.Map;

public interface EventImageRepositoryCustom {
    Map<Long, String> findMainImageUrlByEventInfoId(List<Long> eventInfoIdList);
    String findMainImageUrlByEventInfoId(Long eventInfoId);
}
