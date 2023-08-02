package com.example.openoff.domain.comment.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.comment.application.dto.response.ParentCommentInfoResponseDto;
import com.example.openoff.domain.comment.application.mapper.CommentMapper;
import com.example.openoff.domain.comment.domain.entity.EventComment;
import com.example.openoff.domain.comment.domain.service.CommentService;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentSearchUseCase {
    private final UserUtils userUtils;
    private final EventStaffService eventStaffService;
    private final CommentService commentService;
    private final EventInfoService eventInfoService;

    public PageResponse<ParentCommentInfoResponseDto> getParentCommentsInEvent(Long eventInfo, Long commentId, Pageable pageable) {
        List<String> eventStaffIds = eventStaffService.getEventStaffIds(eventInfo);
        Page<EventComment> eventInfoParentComments = commentService.getEventInfoParentComments(eventInfo, commentId, pageable);
        return CommentMapper.mapToParentCommentInfoResponseDto(eventStaffIds, eventInfoParentComments);
    }
}
