package com.example.openoff.domain.user.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.domain.user.application.dto.request.UserFcmTokenUploadRequestDto;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.entity.UserFcmToken;
import com.example.openoff.domain.user.domain.repository.UserFcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class UserFcmTokenService {
    private final UserFcmTokenRepository userFcmTokenRepository;

    public List<UserFcmToken> findAllByUserIds(String userId) {
        return userFcmTokenRepository.findAllByUser_Id(userId);
    }

    public void save(User user, UserFcmTokenUploadRequestDto userFcmTokenUploadRequestDto) {
        userFcmTokenRepository.findAllByUser_Id(user.getId()).stream()
                .filter(userFcmToken -> userFcmToken.getDeviceId().equals(userFcmTokenUploadRequestDto.getDeviceId()))
                .findAny()
                .ifPresentOrElse(
                        userFcmToken -> {
                            userFcmToken.updateFcmToken(userFcmTokenUploadRequestDto.getFcmToken());
                            userFcmTokenRepository.save(userFcmToken);
                        },
                        () -> userFcmTokenRepository.save(UserFcmToken.builder()
                                .user(user)
                                .deviceId(userFcmTokenUploadRequestDto.getDeviceId())
                                .fcmToken(userFcmTokenUploadRequestDto.getFcmToken())
                                .build())
                );
    }
}
