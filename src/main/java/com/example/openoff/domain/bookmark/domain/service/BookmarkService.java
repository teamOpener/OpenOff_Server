package com.example.openoff.domain.bookmark.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import com.example.openoff.domain.bookmark.domain.repository.EventBookmarkRepository;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class BookmarkService {
    private final EventBookmarkRepository eventBookmarkRepository;


    public boolean doBookmarkAndGetIsExist(EventInfo eventInfo, User user) {
        Optional<EventBookmark> eventBookmarkByEventInfoIdAndUserId = eventBookmarkRepository.findEventBookmarkByEventInfo_IdAndUser_Id(eventInfo.getId(), user.getId());
        if (eventBookmarkByEventInfoIdAndUserId.isPresent()) {
            eventBookmarkRepository.delete(eventBookmarkByEventInfoIdAndUserId.get());
            return true;
        } else {
            eventBookmarkRepository.save(EventBookmark.toEntity(eventInfo, user));
            return false;
        }
    }

    public Page<EventBookmark> findMyBookmarkEvents(String userId, Long bookmarkId, Pageable pageable) {
        return eventBookmarkRepository.findMyBookmarkEvent(userId, bookmarkId, pageable);
    }

    public boolean existsByEventInfo_IdAndUser_Id(Long eventInfoId, String userId) {
        return eventBookmarkRepository.existsByEventInfo_IdAndUser_Id(eventInfoId, userId);
    }
}
