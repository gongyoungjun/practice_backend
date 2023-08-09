package com.example.test.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("JWT Authentication"))  // "JWT Authentication" 보안 요구사항 추가
                .components(new Components().addSecuritySchemes("JWT Authentication", createJWTAuthScheme()))  // JWT 인증
                .info(new Info().title("gong API")  // API의 제목 설정
                        .description("practice API"));  // API의 설명 설정
    }

    // JWT 인증 스키마를 생성하는 메서드
    private SecurityScheme createJWTAuthScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)  // API 키 인증 방식을 사용
                .in(SecurityScheme.In.HEADER)      // 토큰은 헤더를 통해 전달
                .name("Authorization");            // 해당 헤더의 이름은 "Authorization"
    }
}
