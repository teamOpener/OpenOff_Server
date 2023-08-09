package com.example.openoff.domain.comment.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.comment.application.dto.request.CommentChangeRequestDto;
import com.example.openoff.domain.comment.application.dto.response.CommentWriteResponseDto;
import com.example.openoff.domain.comment.application.service.CommentUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentPatchController {
    private final CommentUpdateUseCase commentUpdateUseCase;

    @PatchMapping
    public ResponseEntity<ResponseDto<CommentWriteResponseDto>> updateCommentContent(@RequestBody CommentChangeRequestDto commentChangeRequestDto)
    {
        CommentWriteResponseDto response = commentUpdateUseCase.update(commentChangeRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "댓글 수정이 완료되었습니다.", response));
    }
}
