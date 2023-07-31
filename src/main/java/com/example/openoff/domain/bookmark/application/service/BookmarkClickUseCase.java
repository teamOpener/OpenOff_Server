package com.example.openoff.domain.bookmark.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.bookmark.domain.service.BookmarkService;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class BookmarkClickUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final BookmarkService bookmarkService;

    public void doBookmark(Long eventInfoId) {
        User user = userUtils.getUser();
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventInfoId);
        bookmarkService.doBookmark(eventInfo, user);
    }
}
