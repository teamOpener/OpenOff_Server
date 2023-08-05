package com.example.openoff.domain.comment.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.comment.domain.service.CommentService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class CommentDeleteUseCase {
    private final UserUtils userUtils;
    private final CommentService commentService;

    public void deleteComment(Long commentId) {
        User user = userUtils.getUser();
        commentService.deleteComment(user.getId(), commentId);
    }
}
