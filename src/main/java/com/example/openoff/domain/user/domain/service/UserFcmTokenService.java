package com.example.openoff.domain.user.domain.service;

import com.example.openoff.common.annotation.DomainService;
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

    public void save(User user, String fcmToken) {
        userFcmTokenRepository.findAllByUser_Id(user.getId()).stream()
                        .forEach(userFcmToken -> {
                            if (!userFcmToken.getFcmToken().equals(fcmToken)) {
                                userFcmTokenRepository.save(UserFcmToken.builder().user(user).fcmToken(fcmToken).build());
                            }
                        });
    }
}
