package com.example.openoff.common.util;

import com.example.openoff.common.consts.ApplicationConst;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.security.exception.TokenNotFoundException;
import com.example.openoff.common.security.jwt.JwtAuthenticationToken;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Optional;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SecurityUtils {

    public static String validateHeaderAndGetToken(final String headerValue){
        return Optional.ofNullable(headerValue)
                .filter(header -> header.startsWith(ApplicationConst.JWT_AUTHORIZATION_TYPE))
                .filter(StringUtils::hasText)
                .map(header -> header.replace(ApplicationConst.JWT_AUTHORIZATION_TYPE, ""))
                .orElseThrow(() -> new TokenNotFoundException(Error.INVALID_TOKEN));
    }

    public static String getUserUUID(){
        return (String)getAuthentication().getPrincipal();
    }

    private static JwtAuthenticationToken getAuthentication(){
        return (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
    }

}
