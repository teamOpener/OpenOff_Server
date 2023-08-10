package com.example.openoff.domain.user.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchNicknameResponseDto {
    private String userId;
    private String username;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public SearchNicknameResponseDto(String userId, String username, String nickname, String profileImageUrl) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
