package com.example.openoff.domain.comment.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentWriteResponseDto {
    private Long commentId;
    private Long eventInfoId;
    private Long parentId;
    private String content;

    @Builder
    public CommentWriteResponseDto(Long commentId, Long eventInfoId, Long parentId, String content) {
        this.commentId = commentId;
        this.eventInfoId = eventInfoId;
        this.parentId = parentId;
        this.content = content;
    }
}
