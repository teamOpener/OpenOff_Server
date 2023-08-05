package com.example.openoff.domain.comment.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChildCommentInfoResponseDto {
    private Long commentId;
    private String userId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isStaff;

    @Builder
    public ChildCommentInfoResponseDto(Long commentId, String userId, String nickname, String content, LocalDateTime createdAt, Boolean isStaff) {
        this.commentId = commentId;
        this.userId = userId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
        this.isStaff = isStaff;
    }
}
