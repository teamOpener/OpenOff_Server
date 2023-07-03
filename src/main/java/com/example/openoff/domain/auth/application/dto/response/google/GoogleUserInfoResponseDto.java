package com.example.openoff.domain.auth.application.dto.response.google;

import lombok.Data;

@Data
public class GoogleUserInfoResponseDto {
    private String email;
    private String name;
    private String picture;
}
