package com.example.openoff.domain.bookmark.domain.repository;

import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventBookmarkRepositoryCustom {
   Page<EventBookmark> findMyBookmarkEvent(String userId, Long bookmarkId, Pageable pageable);
   List<EventBookmark> findApply1DayLeftEventBookmark();
}
