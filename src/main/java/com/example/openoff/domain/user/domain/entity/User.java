package com.example.openoff.domain.user.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.auth.domain.entity.SocialAccount;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Getter
@Entity
@Table(name = "openoff_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id", columnDefinition = "VARCHAR(255)", updatable = false, nullable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(name = "user_name")
    private String userName;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "profile_img_url")
    private String profileImageUrl;

    @Embedded
    private Birth birth;

    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "phone_number", unique = true)
    @Pattern(regexp = "^010-?\\d{4}-?\\d{4}$", message = "올바른 한국 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kakao_account_id")
    private SocialAccount kakaoAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "google_account_id")
    private SocialAccount googleAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apple_account_id")
    private SocialAccount appleAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_account_id")
    private SocialAccount normalAccount;

    @Column(name = "terms_conditions_agreement")
    private Boolean termsConditionsAgreement;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Builder
    public User(String userName, String nickname, String profileImageUrl, Birth birth, GenderType gender, String phoneNumber,
                SocialAccount kakaoAccount, SocialAccount googleAccount, SocialAccount appleAccount, SocialAccount normalAccount,
                Boolean termsConditionsAgreement, Boolean isActive) {
        this.userName = userName;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.birth = birth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.kakaoAccount = kakaoAccount;
        this.googleAccount = googleAccount;
        this.appleAccount = appleAccount;
        this.normalAccount = normalAccount;
        this.termsConditionsAgreement = termsConditionsAgreement;
        this.isActive = isActive;
    }

    public void updateBasicUserInfo(String userName, String nickname, int year, int month, int day, GenderType gender){
        this.userName = userName;
        this.nickname = nickname;
        this.birth = Birth.builder()
                .year(year)
                .month(month)
                .day(day)
                .build();
        this.gender = gender;
    }
}
