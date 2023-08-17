package com.example.test.api.kakao.controller;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.kakao.service.KakaoService;
import com.example.test.api.kakao.vo.KakaoFriend;
import com.example.test.api.kakao.vo.KakaoReq;
import com.example.test.api.kakao.vo.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    /**
     * 카카오
     * 인가코드
     */
    @PostMapping("/token")
    public String getKakaoToken(@RequestParam("code") String code) {
        return kakaoService.getAccessToken(code);
    }


    @PostMapping("/extract-sub")
    public ResponseEntity<String> extractSubFromIdToken(@RequestParam("idToken") String idToken) {
        return ResponseEntity.ok(kakaoService.getIdToken(idToken));
    }
}