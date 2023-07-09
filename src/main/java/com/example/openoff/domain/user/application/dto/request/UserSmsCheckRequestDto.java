package com.example.openoff.domain.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSmsCheckRequestDto {
    @NotNull
    private String phoneNum;
    @NotNull
    private String checkNum;
}
