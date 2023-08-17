package com.example.test.api.kakao.vo;

import lombok.Data;

@Data
public class KakaoUserInfo {

    private Long id; // 카카오 사용자 고유 ID
    private String connected_at; // 연결된 날짜
    private String email;
    private String nickname;

}
