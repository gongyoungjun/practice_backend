package com.example.test.api.kakao.service;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpReq;

public interface KakaoService {
    /**
     * 토큰들 가져오기
     */
    String getKaKaoCode(String code);

    /**
     * 사원목록
     * update
     */
    int kakaoEmpUpdate(EmpDTO empDTO);

    /**
     * snsKey가 있는지 체크
     */

    EmpDTO selectSnsKey(EmpReq req);

    /**
     * 카카오 회원가입
     */
    int insertKakaoUser(EmpDTO empDTO);

    /**
     * 사원
     * 상세 정보
     */
    EmpDTO UpdateSnsKey(String snsKey);


}
