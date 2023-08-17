package com.example.test.api.kakao.service;

import com.example.test.api.kakao.vo.KakaoFriend;
import com.example.test.api.kakao.vo.KakaoReq;
import com.example.test.api.kakao.vo.KakaoUserInfo;

import java.util.List;

public interface KakaoService {
    /**
     * 토큰들 가져오기
     * @param authCode
     * @return
     */
    String getAccessToken(String authCode);


    String getIdToken(String idToken);

}
