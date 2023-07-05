package com.example.openoff.common.security.jwt;

import com.example.openoff.common.consts.IgnoredPathConst;
import com.example.openoff.common.exception.dto.ErrorResponse;
import com.example.openoff.common.security.exception.JwtException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO : 에러 반환시에 사용자 정보를 로그로 남기기(?)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (JwtException e){
            log.info("JwtException: {} {}",e.getError().getMessage(), e.getError().getErrorCode());
            setErrorCode(response, e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String ignoredPath : IgnoredPathConst.IGNORED_PATHS) {
            if(antPathMatcher.match(ignoredPath, path)) {
                return false;
            }
        }
        return true;
    }

    private void setErrorCode(HttpServletResponse response, JwtException e) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.from(e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
