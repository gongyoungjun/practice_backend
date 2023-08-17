package com.example.test.api.kakao.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

    /**
     * 토큰들 가져오기
     */
    @Override
    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        // 토큰 요청 파라미터 설정
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", REST_API);
        parameters.add("redirect_uri", REDIRECT_URI);
        parameters.add("code", code);

        ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_TOKEN_URL, parameters, String.class);

        // 여기서 response body 안에는 access_token, refresh_token, id_token 포함
        return response.getBody();
    }

    /**
     * idToken
     * sub 가져오기
     */
    @Override
    public String getIdToken(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);
        return jwt.getClaim("sub").asString();
    }

    public boolean isUserExists(String snsKey) {
        return empDao.isSnsKeyExists(snsKey);
    }


}