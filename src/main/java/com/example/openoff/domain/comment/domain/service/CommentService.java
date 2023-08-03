package com.example.openoff.domain.comment.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.example.openoff.domain.comment.domain.entity.EventComment;
import com.example.openoff.domain.comment.domain.repository.EventCommentRepository;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class CommentService {
    private final EventCommentRepository eventCommentRepository;

    public EventComment insert(User user, EventInfo eventInfo, CommentWriteRequestDto commentWriteRequestDto){
        EventComment entity = EventComment.toEntity(user, eventInfo, commentWriteRequestDto.getContent());
        if (commentWriteRequestDto.getParentId() != null) {
            EventComment parent = eventCommentRepository.findById(commentWriteRequestDto.getParentId())
                    .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
            entity.updateParent(parent);
        }
        return eventCommentRepository.save(entity);
    }

    public Page<EventComment> getEventInfoParentComments(Long eventInfoId, Long commentId, Pageable pageable){
        return eventCommentRepository.getParentEventComments(eventInfoId, commentId, pageable);
    }

    public List<EventComment> getEventInfoChildComments(Long eventInfoId, Long commentId) {
        return eventCommentRepository.getChildEventComments(eventInfoId, commentId);
    }

    public void deleteComment(String userId, Long commentId) {
        eventCommentRepository.findEventCommentByIdAndWriter_Id(commentId, userId)
                .ifPresentOrElse(eventCommentRepository::delete,
                        // 댓글 삭제 권한이 없는 예외 처리 추가해야함
                        () -> {throw BusinessException.of(Error.DATA_NOT_FOUND);}
                );

    }
}
