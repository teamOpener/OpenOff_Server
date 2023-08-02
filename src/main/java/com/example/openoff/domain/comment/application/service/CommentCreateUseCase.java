package com.example.openoff.domain.comment.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.example.openoff.domain.comment.application.dto.response.CommentWriteResponseDto;
import com.example.openoff.domain.comment.application.mapper.CommentMapper;
import com.example.openoff.domain.comment.domain.entity.EventComment;
import com.example.openoff.domain.comment.domain.service.CommentService;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class CommentCreateUseCase {
    private final UserUtils userUtils;
    private final CommentService commentService;
    private final EventInfoService eventInfoService;

    public CommentWriteResponseDto insert(CommentWriteRequestDto commentWriteRequestDTO) {
        User user = userUtils.getUser();
        EventInfo eventInfo = eventInfoService.findEventInfoById(commentWriteRequestDTO.getEventInfoId());

        EventComment comment = commentService.insert(user, eventInfo, commentWriteRequestDTO);
        return CommentMapper.mapToCommentWriteResDto(comment, commentWriteRequestDTO);
    }
}
