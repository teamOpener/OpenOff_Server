package com.example.openoff.domain.comment.presentation;

import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.comment.application.dto.response.ChildCommentInfoResponseDto;
import com.example.openoff.domain.comment.application.dto.response.ParentCommentInfoResponseDto;
import com.example.openoff.domain.comment.application.service.CommentSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                    @PageableDefault(size = 8) Pageable pageable
            )
    {
        PageResponse<ParentCommentInfoResponseDto> parentCommentsInEvent = commentSearchUseCase.getParentCommentsInEvent(eventInfoId, commentId, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "댓글 조회가 완료되었습니다.", parentCommentsInEvent));
    }

    @GetMapping(value = "/child/{eventInfoId}/{commentId}")
    public ResponseEntity<ResponseDto<List<ChildCommentInfoResponseDto>>> getChildCommentsInEvent
            (
                    @PathVariable Long eventInfoId, @PathVariable Long commentId
            )
    {
        List<ChildCommentInfoResponseDto> childCommentsInEvent = commentSearchUseCase.getChildCommentsInEvent(eventInfoId, commentId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "대댓글 조회 완료되었습니다.", childCommentsInEvent));
    }
}
