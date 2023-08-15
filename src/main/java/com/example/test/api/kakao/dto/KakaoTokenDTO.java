package com.example.test.api.kakao.dto;

import lombok.Data;

@Data
public class KakaoTokenDTO {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;

}