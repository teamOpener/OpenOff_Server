package com.example.openoff.domain.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFcmTokenUploadRequestDto {
    private String deviceId;
    private String fcmToken;
}
