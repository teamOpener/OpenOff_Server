package com.example.openoff.domain.comment.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentWriteRequestDto {
    private Long eventInfoId;
    private Long parentId;
    private String content;
}
