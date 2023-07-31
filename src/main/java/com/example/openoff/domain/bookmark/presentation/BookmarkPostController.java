package com.example.openoff.domain.bookmark.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.bookmark.application.service.BookmarkClickUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bookmark")
@RequiredArgsConstructor
public class BookmarkPostController {
    private final BookmarkClickUseCase bookmarkClickUseCase;

    @PostMapping(value = "/{eventInfoId}")
    public ResponseEntity<ResponseDto<Void>> doBookmark(@PathVariable Long eventInfoId)
    {
        bookmarkClickUseCase.doBookmark(eventInfoId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 즐겨찾기 (등록/취소)를 성공하였습니다.", null));
    }
}
