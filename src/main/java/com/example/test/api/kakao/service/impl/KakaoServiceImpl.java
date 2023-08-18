package com.example.test.api.kakao.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.kakao.service.KakaoService;
import com.example.test.api.kakao.vo.KakaoUserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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
     * id-token
     * 인코딩을 디코딩으로
     */
    @Override
    public String getIdToken(String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWT.require(algorithm).build(); // token verifier
            DecodedJWT jwt = JWT.decode(jwtToken);
            return jwt.getClaim("sub").asString();
        } catch (Exception e) {
            new RuntimeException("인코딩을 디코딩 쪽 에러", e);
        }
        return jwtToken;
    }

    /**
     * 카카오 snskey 비교
     */
    @Override
    public EmpDTO selectSnsKey(String snsKey) {
        return empDao.selectSnsKey(snsKey);
    }

    /**
     * 카카오 회원가입
     */
    @Override
    public int insertKakaoUser(EmpDTO empDTO) {
        return empDao.insertKakaoUser(empDTO);
    }




}


/*    public String getKaKaoCode(String authcode) {
        RestTemplate restTemplate = new RestTemplate();

        // 토큰 요청 파라미터 설정
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", REST_API);
        parameters.add("redirect_uri", REDIRECT_URI);
        parameters.add("code", authcode);

        ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_TOKEN_URL, parameters, String.class);
        log.info("Kakao token response: {}", response.getBody());


        return response.getBody();
    }*/

/*    public EmpRes getAccessToken(String authCode) {
        EmpRes empRes = new EmpRes();
        RestTemplate restTemplate = new RestTemplate();

        // 토큰 요청 파라미터 설정
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", REST_API);
        parameters.add("redirect_uri", REDIRECT_URI);
        parameters.add("code", authCode);

        //토큰 요청을 보내고 응답
        ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_TOKEN_URL, parameters, String.class);
        log.info("Kakao token : {}", response.getBody());

        // 액세스 토큰 정보를 empRes에 설정
        empRes.setAccessToken(response.getBody());

        return empRes;
    }*/        //String idToken = getIdToken(response.getBody());


// access_token과 id_token을 Map으로 담아 반환
/*        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", response.getBody());
        tokens.put("id_token", idToken);

        return tokens;*/