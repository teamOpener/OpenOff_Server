package com.example.openoff.domain.bookmark.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.bookmark.domain.service.BookmarkService;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.entity.UserFcmToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class BookmarkClickUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final BookmarkService bookmarkService;
    private final FirebaseService firebaseService;

    public void doBookmark(Long eventInfoId) {
        User user = userUtils.getUser();
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventInfoId);

        boolean getIsExist = bookmarkService.doBookmarkAndGetIsExist(eventInfo, user);

        List<String> fcmTokens = user.getUserFcmTokens().stream()
                .map(UserFcmToken::getFcmToken)
                .collect(Collectors.toList());
        if (fcmTokens.isEmpty()) {
            return;
        } else {
            String[] suffixes = {"-bookmark-1day", "-bookmark-half", "-bookmark-almost"};
            if (getIsExist){
                for (String suffix : suffixes) {
                    firebaseService.unSubscribe(eventInfo.getId() + suffix, fcmTokens);
                }
            } else {
                for (String suffix : suffixes) {
                    firebaseService.subScribe(eventInfo.getId() + suffix, fcmTokens);
                }
            }
        }

    }
}
