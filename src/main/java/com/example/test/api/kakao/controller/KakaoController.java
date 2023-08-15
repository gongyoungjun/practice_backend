package com.example.test.api.kakao.controller;

import com.example.test.api.kakao.service.KakaoService;
import com.example.test.api.kakao.vo.KakaoFriend;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    /**
     * 카카오 친구목록
     */
    @PostMapping("/friends")
    public ResponseEntity<List<KakaoFriend>> getFriends(@RequestParam String accessToken) {
        try {
            List<KakaoFriend> friendsList = kakaoService.getFriendsList(accessToken);
            return ResponseEntity.ok(friendsList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
