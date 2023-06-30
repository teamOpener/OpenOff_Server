package com.example.openoff.common.util;

import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserQueryService userQueryService;

    public User getUser(){
        final String userEmail = SecurityUtils.getUserEmail();
        return userQueryService.findByEmail(userEmail);
    }
}
