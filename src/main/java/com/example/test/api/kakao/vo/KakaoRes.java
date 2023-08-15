package com.example.test.api.kakao.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
public class KakaoRes {

    private String code;

    @Schema(description = "카카오 토큰")
    private List<KakaoFriend> Friend;

}
