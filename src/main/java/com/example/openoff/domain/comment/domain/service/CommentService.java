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
}
