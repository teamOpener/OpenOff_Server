package com.example.openoff.domain.user.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "phone_number", unique = true)
    @Pattern(regexp = "^010-?\\d{4}-?\\d{4}$", message = "올바른 한국 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber;

    @Column(name = "email")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Column(name = "password", length = 300)
    private String password;

    private String nickname;
    private String profileImageUrl;
}
