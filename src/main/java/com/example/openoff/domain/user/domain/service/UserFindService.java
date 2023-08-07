package com.example.openoff.domain.user.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.exception.UserNotFoundException;
import com.example.openoff.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class UserFindService {
    private final UserRepository userRepository;

    public User findBySocialId(Long socialId) {
        return userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new UserNotFoundException(Error.USER_NOT_FOUND));
    }

    public User findByUUID(String uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(Error.USER_NOT_FOUND));
    }

    public User findByPhoneNum(String phoneNum) {
        return userRepository.findByPhoneNumber(phoneNum)
                .orElseThrow(() -> new UserNotFoundException(Error.USER_NOT_FOUND));
    }
}
