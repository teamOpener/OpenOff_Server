package com.example.openoff.domain.comment.domain.repository;

import com.example.openoff.domain.comment.domain.entity.EventComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventCommentRepository extends JpaRepository<EventComment, Long>, EventCommentRepositoryCustom {
    Optional<EventComment> findEventCommentByIdAndWriter_Id(Long id, String userId);
}
