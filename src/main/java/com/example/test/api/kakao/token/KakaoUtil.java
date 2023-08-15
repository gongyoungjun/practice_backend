/*
package com.example.test.api.kakao.token;

import com.example.test.api.kakao.vo.KakaoFriend;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class KakaoUtil {

    @Value("${kakao.jwt.secret}")
    private String kakaoJwtSecret;

    private static final String KAKAO_FRIENDS_PERMISSION_URL = "https://kapi.kakao.com/v1/api/talk/friends/permission";
    private static final String KAKAO_FRIENDS_LIST_URL = "https://kapi.kakao.com/v1/api/talk/friends";

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(kakaoJwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(kakaoJwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean requestFriendsPermission(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_FRIENDS_PERMISSION_URL, HttpMethod.GET, entity, String.class
        );

        return response.getStatusCode() == HttpStatus.OK;
    }

    public List<KakaoFriend> getFriendsList(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<KakaoFriend>> response = restTemplate.exchange(
                KAKAO_FRIENDS_LIST_URL,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<KakaoFriend>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve Kakao friends list. Response: " + response.getStatusCode());
        }
    }
*/
