package com.example.openoff.domain.comment.domain.repository;

import com.example.openoff.domain.comment.domain.entity.EventComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventCommentRepositoryCustom {
    Page<EventComment> getParentEventComments(Long eventInfoId, Long commentId, Pageable pageable);
    List<EventComment> getChildEventComments(Long eventInfoId, Long commentId);
}
