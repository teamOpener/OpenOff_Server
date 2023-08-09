package com.example.openoff.domain.user.application.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckNicknameResponseDto {
    private Boolean isExist;

    @Builder
    public CheckNicknameResponseDto(Boolean isExist) {
        this.isExist = isExist;
    }
}
