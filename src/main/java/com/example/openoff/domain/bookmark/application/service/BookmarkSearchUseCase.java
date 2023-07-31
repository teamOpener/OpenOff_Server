package com.example.openoff.domain.bookmark.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.bookmark.application.dto.response.MyBookmarkEventResponseDto;
import com.example.openoff.domain.bookmark.application.mapper.BookmarkMapper;
import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import com.example.openoff.domain.bookmark.domain.service.BookmarkService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class BookmarkSearchUseCase {
    private final UserUtils userUtils;
    private final BookmarkService bookmarkService;

    public PageResponse<MyBookmarkEventResponseDto> findMyBookmarks(Long bookmarkId, Pageable pageable){
        User user = userUtils.getUser();
        Page<EventBookmark> myBookmarkEvents = bookmarkService.findMyBookmarkEvents(user.getId(), bookmarkId, pageable);
        return BookmarkMapper.toMyBookmarkEventResponseDto(myBookmarkEvents);
    }
}
