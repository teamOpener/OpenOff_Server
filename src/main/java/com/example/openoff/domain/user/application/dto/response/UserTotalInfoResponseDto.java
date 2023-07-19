package com.example.openoff.domain.user.application.dto.response;

import com.example.openoff.domain.auth.application.dto.response.SocialAccountInfoResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTotalInfoResponseDto {
    private UserInfoResponseDto userInfo;
    private List<SocialAccountInfoResponseDto> socialAccountInfoList;

    @Builder
    public UserTotalInfoResponseDto(UserInfoResponseDto userInfo, List<SocialAccountInfoResponseDto> socialAccountInfoList) {
        this.userInfo = userInfo;
        this.socialAccountInfoList = socialAccountInfoList;
    }

    public static UserTotalInfoResponseDto from(UserInfoResponseDto userInfo, List<SocialAccountInfoResponseDto> socialAccountInfoList){
        return UserTotalInfoResponseDto.builder()
                .userInfo(userInfo)
                .socialAccountInfoList(socialAccountInfoList)
                .build();
    }
}
