package com.example.test.api.kakao.vo;

import com.example.test.api.kakao.dto.KakaoTokenDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
public class KakaoRes {

    @Schema(description = "응답 코드")
    private String code;
    @Schema(description = "카카오 토큰")
    private String accessToken;
    @Schema(description = "카카오 리스트")
    private List<KakaoTokenDTO> kakaList;

}
