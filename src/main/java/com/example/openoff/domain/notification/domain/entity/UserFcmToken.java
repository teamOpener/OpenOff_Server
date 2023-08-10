package com.example.openoff.domain.notification.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "userFcmToken", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @Builder
    public UserFcmToken(User user, String fcmToken) {
        this.user = user;
        this.fcmToken = fcmToken;
    }
}
