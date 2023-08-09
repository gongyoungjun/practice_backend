package com.example.test.api.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization"); // Authorization 헤더에서 토큰을 가져옵니다.

        if (token != null && JwtUtil.isTokenValid(token)) {
            return true;
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰X 유효X"); // 토큰x 유효x 에러 반환.
        return false;
    }
}