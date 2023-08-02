package com.example.openoff.domain.comment.presentation;

import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.comment.application.dto.response.ParentCommentInfoResponseDto;
import com.example.openoff.domain.comment.application.service.CommentSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentGetController {
    private final CommentSearchUseCase commentSearchUseCase;

    @GetMapping(value = "/{eventInfoId}")
    public ResponseEntity<ResponseDto<PageResponse<ParentCommentInfoResponseDto>>> getParentCommentsInEvent
            (
                    @PathVariable Long eventInfoId,
                    @RequestParam(required = false) Long commentId,
                    @PageableDefault(size = 1) Pageable pageable
            )
    {
        PageResponse<ParentCommentInfoResponseDto> parentCommentsInEvent = commentSearchUseCase.getParentCommentsInEvent(eventInfoId, commentId, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "댓글 작성이 완료되었습니다.", parentCommentsInEvent));
    }
}
