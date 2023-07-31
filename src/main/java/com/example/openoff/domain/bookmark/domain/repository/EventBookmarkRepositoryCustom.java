package com.example.openoff.domain.bookmark.domain.repository;

import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventBookmarkRepositoryCustom {
   Page<EventBookmark> findMyBookmarkEvent(String userId, Long bookmarkId, Pageable pageable);
}
