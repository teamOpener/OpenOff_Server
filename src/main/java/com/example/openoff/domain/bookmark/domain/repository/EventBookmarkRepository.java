package com.example.openoff.domain.bookmark.domain.repository;

import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventBookmarkRepository extends JpaRepository<EventBookmark, Long>, EventBookmarkRepositoryCustom {
    @Query("SELECT e.eventInfo.id FROM EventBookmark e WHERE e.eventInfo.id in :eventInfoIdList AND e.user.id = :userId")
    List<Long> findByEventInfoIdAndUserId(List<Long> eventInfoIdList, String userId);

    Optional<EventBookmark> findEventBookmarkByEventInfo_IdAndUser_Id(Long eventInfoId, String userId);

    boolean existsByEventInfo_IdAndUser_Id(Long eventInfoId, String userId);

    List<EventBookmark> findEventBookmarkByEventInfo_Id(Long eventInfoId);
}
