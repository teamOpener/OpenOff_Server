package com.example.openoff.domain.bookmark.presentation;

import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.bookmark.application.dto.response.MyBookmarkEventResponseDto;
import com.example.openoff.domain.bookmark.application.service.BookmarkSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bookmark")
@RequiredArgsConstructor
public class BookmarkGetController {
    private final BookmarkSearchUseCase bookmarkSearchUseCase;

    @GetMapping(value = "")
    public ResponseEntity<ResponseDto<PageResponse<MyBookmarkEventResponseDto>>> getMyBookmarkEventList
            (
                    @RequestParam(required = false) Long bookmarkId,
                    @PageableDefault(size = 1) Pageable pageable
            )
    {
        PageResponse<MyBookmarkEventResponseDto> myBookmarks = bookmarkSearchUseCase.findMyBookmarks(bookmarkId, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "북마크 이벤트 리스트 조회에 성공하였습니다.", myBookmarks));
    }

}
