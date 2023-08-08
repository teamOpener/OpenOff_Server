package com.example.openoff.domain.auth.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "openoff_social_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_account_id")
    private Long id;

//    @Convert(converter = AccountTypeConverter.class)
    @Column(name = "account_type", nullable = false, updatable = false)
    private AccountType accountType;

    // 일반 유저일 경우 password로 사용
   @Column(name = "social_id", unique = true)
    private String socialId;

    @Column(name = "email", updatable = false)
    private String email;

    @Column(name = "social_name")
    private String socialName;

    @Builder
    public SocialAccount(AccountType accountType, String socialId, String email, String socialName) {
        this.accountType = accountType;
        this.socialId = socialId;
        this.email = email;
        this.socialName = socialName;
    }

    public static SocialAccount toEntity(AccountType accountType, String socialId, String email, String socialName){
        return SocialAccount.builder()
                .accountType(accountType)
                .socialId(socialId)
                .email(email)
                .socialName(socialName)
                .build();
    }

    public void updateNormalAccountSocialId(String newSocialId) {
        this.socialId = newSocialId;
    }
}
