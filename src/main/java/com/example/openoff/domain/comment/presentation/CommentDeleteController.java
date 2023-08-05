package com.example.openoff.domain.comment.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.comment.application.service.CommentDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentDeleteController {
    private final CommentDeleteUseCase commentDeleteUseCase;

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<ResponseDto<Void>> deleteComment(@PathVariable Long commentId)
    {
        commentDeleteUseCase.deleteComment(commentId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "댓글 삭제가 완료되었습니다.", null));
    }
}
