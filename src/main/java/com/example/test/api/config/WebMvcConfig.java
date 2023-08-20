package com.example.test.api.config;

import com.example.test.api.jwt.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login")  // 로그인 경로는 JWT 검증제외
                .excludePathPatterns("/api/emp/emailCheck") // 회원가입 JWT 검증제외
                .excludePathPatterns("/api/kakao/signup")
                .excludePathPatterns("/api/kakao/token")
                .excludePathPatterns("/api/kakao/snsKey")
                .excludePathPatterns("/api/kakao/detail")
                .excludePathPatterns("/api/kakao/update")
                .excludePathPatterns("/api/signup");
    }
}