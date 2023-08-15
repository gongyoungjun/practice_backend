package com.example.test.api.kakao.service.impl;

import com.example.test.api.kakao.service.KakaoService;
import com.example.test.api.kakao.vo.KakaoFriend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private static final String KAKAO_FRIENDS_LIST_URL = "https://kapi.kakao.com/v1/api/talk/friends";

    public List<KakaoFriend> getFriendsList(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<KakaoFriend>> response = restTemplate.exchange(
                KAKAO_FRIENDS_LIST_URL,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve Kakao friends list. Response: " + response.getStatusCode());
        }
    }
}
