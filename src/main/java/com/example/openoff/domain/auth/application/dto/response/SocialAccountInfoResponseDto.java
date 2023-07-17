package com.example.openoff.domain.auth.application.dto.response;

import com.example.openoff.domain.auth.domain.entity.AccountType;
import com.example.openoff.domain.auth.domain.entity.SocialAccount;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccountInfoResponseDto {
    private Long id;
    private AccountType accountType;
    private String socialId;
    private String email;
    private String socialName;

    @Builder
    public SocialAccountInfoResponseDto(Long id, AccountType accountType, String socialId, String email, String socialName) {
        this.id = id;
        this.accountType = accountType;
        this.socialId = socialId;
        this.email = email;
        this.socialName = socialName;
    }

    public static SocialAccountInfoResponseDto of(SocialAccount socialAccount){
        return SocialAccountInfoResponseDto.builder()
                .id(socialAccount.getId())
                .accountType(socialAccount.getAccountType())
                .socialId(socialAccount.getSocialId())
                .email(socialAccount.getEmail())
                .socialName(socialAccount.getSocialName())
                .build();
    }

    public static List<SocialAccountInfoResponseDto> ofList(List<SocialAccount> socialAccountList){
        return socialAccountList.stream()
                .map(SocialAccountInfoResponseDto::of)
                .collect(Collectors.toList());
    }
}
