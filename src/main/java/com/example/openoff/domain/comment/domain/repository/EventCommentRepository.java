package com.example.openoff.domain.comment.domain.repository;

import com.example.openoff.domain.comment.domain.entity.EventComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCommentRepository extends JpaRepository<EventComment, Long>, EventCommentRepositoryCustom {
}
