package com.example.openoff.domain.user.application.dto.response;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.user.domain.entity.Birth;
import com.example.openoff.domain.user.domain.entity.GenderType;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDto {
    private String uuid;
    private String userName;
    private String nickname;
    private String profileImageUrl;
    private Birth birth;
    private GenderType gender;
    private String phoneNumber;
    private List<FieldType> fieldTypeList;

    @Builder
    public UserInfoResponseDto(String uuid, String userName, String nickname, String profileImageUrl, Birth birth, GenderType gender, String phoneNumber, List<FieldType> fieldTypeList) {
        this.uuid = uuid;
        this.userName = userName;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.birth = birth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.fieldTypeList = fieldTypeList;
    }

    @Builder
    public static UserInfoResponseDto from(User user){
        return UserInfoResponseDto.builder()
                .uuid(user.getId())
                .userName(user.getUserName())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .birth(user.getBirth())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .fieldTypeList(user.getUserInterestList())
                .build();
    }
}
