package com.example.test.api.kakao.service;

import com.example.test.api.kakao.vo.KakaoFriend;

import java.util.List;

public interface KakaoService {

    List<KakaoFriend> getFriendsList(String accessToken) throws Exception;
}
