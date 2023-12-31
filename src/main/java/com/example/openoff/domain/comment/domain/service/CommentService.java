package com.example.openoff.domain.comment.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.comment.application.dto.request.CommentChangeRequestDto;
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
import java.util.Optional;

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

    public void deleteComment(Long commentId) {
        eventCommentRepository.deleteById(commentId);
    }

    public EventComment updateCommentByUser(User user, CommentChangeRequestDto commentChangeRequestDto) {
        Optional<EventComment> comment = eventCommentRepository.findEventCommentByIdAndWriter_Id(commentChangeRequestDto.getCommentId(), user.getId());
        if (comment.isEmpty()) {
            throw BusinessException.of(Error.DATA_NOT_FOUND);
        } else {
            EventComment eventComment = comment.get();
            eventComment.updateContent(commentChangeRequestDto.getContent());
            eventCommentRepository.save(eventComment);
            return eventComment;
        }
    }

    public EventComment findByCommentId(Long commentId) {
        return eventCommentRepository.findById(commentId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }
}
