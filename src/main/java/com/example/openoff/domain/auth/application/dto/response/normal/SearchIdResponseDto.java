package com.example.openoff.domain.auth.application.dto.response.normal;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchIdResponseDto {
    private String id;

    @Builder
    public SearchIdResponseDto(String id) {
        this.id = id;
    }
}
