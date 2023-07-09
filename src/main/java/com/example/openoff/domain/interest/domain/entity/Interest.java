package com.example.openoff.domain.interest.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "openoff_user_interest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_interest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "interest", nullable = false)
    private InterestType interestType;

    @Builder
    public Interest(User user, InterestType interestType) {
        this.user = user;
        this.interestType = interestType;
    }

    public static Interest toEntity(User user, InterestType interestType){
        return Interest.builder()
                .user(user)
                .interestType(interestType)
                .build();
    }
}
