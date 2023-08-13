package com.example.openoff.domain.user.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "openoff_user_fcm_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFcmToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_fcm_token_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Builder
    public UserFcmToken(User user, String fcmToken) {
        this.user = user;
        this.fcmToken = fcmToken;
    }
}
