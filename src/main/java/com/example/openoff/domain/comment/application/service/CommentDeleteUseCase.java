package com.example.openoff.domain.comment.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.comment.domain.entity.EventComment;
import com.example.openoff.domain.comment.domain.service.CommentService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class CommentDeleteUseCase {
    private final UserUtils userUtils;
    private final CommentService commentService;

    public void deleteComment(Long commentId) {
        User user = userUtils.getUser();
        EventComment comment = commentService.findByCommentId(commentId);
        List<String> staffId = comment.getEventInfo().getEventStaffs().stream().map(eventStaff -> eventStaff.getStaff().getId()).collect(Collectors.toList());
        if (!staffId.contains(user.getId()) || !comment.getWriter().getId().equals(user.getId())) {
            throw BusinessException.of(Error.NOT_DELETE_AUTHORITY);
        } else {
            commentService.deleteComment(commentId);
        }
    }
}
