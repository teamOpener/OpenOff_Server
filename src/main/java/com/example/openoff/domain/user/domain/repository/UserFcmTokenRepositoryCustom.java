package com.example.openoff.domain.user.domain.repository;

import java.util.List;

public interface UserFcmTokenRepositoryCustom {
    List<String> findAllFcmTokens(List<String> userIds);
}
