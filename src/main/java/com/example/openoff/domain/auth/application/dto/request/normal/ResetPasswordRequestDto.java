package com.example.openoff.domain.auth.application.dto.request.normal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPasswordRequestDto {
    @Email
    private String email;
    @NotNull
    private String phoneNum;
    @NotNull
    private String newPassword;
}
