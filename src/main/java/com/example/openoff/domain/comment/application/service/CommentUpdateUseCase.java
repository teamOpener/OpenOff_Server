package com.example.openoff.domain.comment.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.comment.application.dto.request.CommentChangeRequestDto;
import com.example.openoff.domain.comment.application.dto.response.CommentWriteResponseDto;
import com.example.openoff.domain.comment.application.mapper.CommentMapper;
import com.example.openoff.domain.comment.domain.entity.EventComment;
import com.example.openoff.domain.comment.domain.service.CommentService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class CommentUpdateUseCase {
    private final UserUtils userUtils;
    private final CommentService commentService;

    public CommentWriteResponseDto update(CommentChangeRequestDto commentChangeRequestDto) {
        User user = userUtils.getUser();
        EventComment comment = commentService.updateCommentByUser(user, commentChangeRequestDto);
        return CommentMapper.mapToCommentWriteResDto(comment);
    }
}
