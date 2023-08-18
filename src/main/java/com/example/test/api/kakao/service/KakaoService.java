package com.example.test.api.kakao.service;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.kakao.vo.KakaoFriend;
import com.example.test.api.kakao.vo.KakaoReq;
import com.example.test.api.kakao.vo.KakaoUserInfo;

import java.util.List;
import java.util.Map;

public interface KakaoService {
    /**
     * 토큰들 가져오기
     */
    String getKaKaoCode(String code);

    /**
     * id-token
     */
    String getIdToken(String jwtToken);

    /**
     * snsKey가 있는지 체크
     */

    EmpDTO selectSnsKey(String snsKey);

    /**
     * 카카오 회원가입
     */
    int insertKakaoUser(EmpDTO empDTO);




}
