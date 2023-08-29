package com.example.test.api.kakao.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final EmpDao empDao;


    @Value("${kakao.token.url}")
    private String KAKAO_TOKEN_URL;

    @Value("${kakao.rest.api}")
    private String REST_API;

    @Value("${kakao.redirect.uri}")
    private String REDIRECT_URI;

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    /**
     * 토큰들 가져오기
     */
    @Override
    public String getKaKaoCode(String authCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", REST_API);
        parameters.add("redirect_uri", REDIRECT_URI);
        parameters.add("code", authCode);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(KAKAO_TOKEN_URL, HttpMethod.POST, requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        log.info("Kakao token response: {}", responseBody);

        return responseBody;
    }

    /**
     * 카카오 snskey 비교
     */
    @Override
    public EmpDTO selectSnsKey(EmpReq req) {
        return empDao.selectSnsKey(req);
    }

    /**
     * 카카오 회원가입
     */
    @Override
    public int insertKakaoUser(EmpDTO empDTO) {
        return empDao.insertKakaoUser(empDTO);
    }

    /**
     * 사원목록
     * update
     */
    @Override
    public int kakaoEmpUpdate(EmpDTO empDTO) {
        return empDao.kakaoEmpUpdate(empDTO);
    }

    /**
     * 사원
     * 상세 목록
     */
    public EmpDTO UpdateSnsKey(String snsKey) {
        return empDao.UpdateSnsKey(snsKey);
    }


}

