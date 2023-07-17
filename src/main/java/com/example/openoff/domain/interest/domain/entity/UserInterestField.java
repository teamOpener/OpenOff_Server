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
@Table(name = "openoff_user_interest_field")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterestField extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_interest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "interest", nullable = false)
    private FieldType fieldType;

    @Builder
    public UserInterestField(User user, FieldType fieldType) {
        this.user = user;
        this.fieldType = fieldType;
    }

    public static UserInterestField toEntity(User user, FieldType fieldType){
        return UserInterestField.builder()
                .user(user)
                .fieldType(fieldType)
                .build();
    }
}
