package com.example.openoff.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class SliceResponse<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final boolean hasNext;

    @Builder
    private SliceResponse(List<T> content, int page, int size, boolean hasNext) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
    }

    public static <T> SliceResponse<T> of(Slice<T> slice) {
        return new SliceResponse<>(slice.getContent(), slice.getNumber(), slice.getSize(), slice.hasNext());
    }
}
