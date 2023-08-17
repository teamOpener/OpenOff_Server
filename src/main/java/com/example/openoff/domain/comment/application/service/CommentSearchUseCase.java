package com.example.openoff.domain.comment.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.comment.application.dto.response.ChildCommentInfoResponseDto;
import com.example.openoff.domain.comment.application.dto.response.ParentCommentInfoResponseDto;
import com.example.openoff.domain.comment.application.mapper.CommentMapper;
import com.example.openoff.domain.comment.domain.entity.EventComment;
import com.example.openoff.domain.comment.domain.service.CommentService;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.user.domain.entity.UserFcmToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentSearchUseCase {
    private final UserUtils userUtils;
    private final EventStaffService eventStaffService;
    private final CommentService commentService;
    private final FirebaseService firebaseService;

    public PageResponse<ParentCommentInfoResponseDto> getParentCommentsInEvent(Long eventInfoId, Long commentId, Pageable pageable) {
        List<String> eventStaffIds = eventStaffService.getEventStaffIds(eventInfoId);
        Page<EventComment> eventInfoParentComments = commentService.getEventInfoParentComments(eventInfoId, commentId, pageable);
        return CommentMapper.mapToParentCommentInfoResponseDto(eventStaffIds, eventInfoParentComments);
    }

    public List<ChildCommentInfoResponseDto> getChildCommentsInEvent(Long eventInfoId, Long commentId) {
        List<String> eventStaffIds = eventStaffService.getEventStaffIds(eventInfoId);
        List<EventComment> eventInfoChildComments = commentService.getEventInfoChildComments(eventInfoId, commentId);
        return CommentMapper.mapToChildCommentInfoResponseDto(eventStaffIds, eventInfoChildComments);
    }

    public void reportComment(Long commentId) {
        EventComment comment = commentService.findByCommentId(commentId);
        List<UserFcmToken> fcmTokens = comment.getEventInfo().getEventStaffs().stream()
                .flatMap(eventStaff -> eventStaff.getStaff().getUserFcmTokens().stream())
                .collect(Collectors.toList());

        if (!fcmTokens.isEmpty()) {
            firebaseService.sendFCMNotificationMulticast(fcmTokens, "댓글 신고", "내가 남긴 댓글이 신고되었어요.");
        }
    }
}
