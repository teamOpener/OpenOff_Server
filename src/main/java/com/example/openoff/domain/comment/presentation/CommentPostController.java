package com.example.openoff.domain.comment.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.example.openoff.domain.comment.application.dto.response.CommentWriteResponseDto;
import com.example.openoff.domain.comment.application.service.CommentCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentPostController {
    private final CommentCreateUseCase commentCreateUseCase;

    @PostMapping
    public ResponseEntity<ResponseDto<CommentWriteResponseDto>> createParentComment(@RequestBody CommentWriteRequestDto commentWriteRequestDto)
    {
        CommentWriteResponseDto response = commentCreateUseCase.insert(commentWriteRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "댓글 작성이 완료되었습니다.", response));
    }
}
