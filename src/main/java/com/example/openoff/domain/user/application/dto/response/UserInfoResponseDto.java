package com.example.openoff.domain.user.application.dto.response;

import com.example.openoff.domain.user.domain.entity.Birth;
import com.example.openoff.domain.user.domain.entity.GenderType;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDto {
    private String userName;
    private String nickname;
    private String profileImageUrl;
    private Birth birth;
    private GenderType gender;
    private String phoneNumber;

    @Builder
    public UserInfoResponseDto(String userName, String nickname, String profileImageUrl, Birth birth, GenderType gender, String phoneNumber) {
        this.userName = userName;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.birth = birth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public static UserInfoResponseDto from(User user){
        return UserInfoResponseDto.builder()
                .userName(user.getUserName())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .birth(user.getBirth())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
