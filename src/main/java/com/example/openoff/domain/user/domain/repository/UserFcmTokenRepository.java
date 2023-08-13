package com.example.openoff.domain.user.domain.repository;

import com.example.openoff.domain.user.domain.entity.UserFcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, Long> {
    List<UserFcmToken> findAllByUser_Id(String userId);
}
