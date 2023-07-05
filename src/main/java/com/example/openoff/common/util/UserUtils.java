package com.example.openoff.common.util;

import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserFindService userFindService;

    public User getUser(){
        final String userUUID = SecurityUtils.getUserUUID();
        return userFindService.findByUUID(userUUID);
    }
}
