package com.example.test.api.apiTest.service.impl;

import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import com.example.test.api.apiTest.service.ApiTestService;
import com.example.test.api.apiTest.vo.ApiRes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiTestServiceImpl implements ApiTestService {

    private static final String API_MEMBER_URL = "http://mjgolf.myqnapcloud.com/api/admin/memberList";
    private static final String API_LESSON_URL = "http://mjgolf.myqnapcloud.com/api/lesson/list";
    private static final String AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODQ4MTA4OTAsInN1YiI6IjExMiIsImV4cCI6MTcxNjM0Njg5MH0.r98D7N7cdpoCVKpW_bUVTOXwHllgt83toirt_E6nCH8";


    @Tag(name = "RestTemplate - 회원목록")
    @Override
    public List<ApiMemberDTO> apiTestList() {
        return ApiAndGetList(API_MEMBER_URL, "list");
    }

    @Tag(name = "RestTemplate - 회원별 레슨")
    @Override
    public List<ApiLessonDTO> lessonMemberView() {
        return ApiAndGetList(API_LESSON_URL, "lessonList");
    }

    public <T> List<T> ApiAndGetList(String apiUrl, String dataNodeName) {
        List<T> dataList = new ArrayList<>();

        try {
            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 설정
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", AUTH_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 본문 데이터 생성
            String requestData = "{}"; // 요청 본문 데이터 설정

            // HttpEntity(http요청) 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, ApiRes.class);
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    JsonNode dataNode = new ObjectMapper().valueToTree(apiRes.getData());
                    if (dataNode.has(dataNodeName)) {
                        dataList = new ObjectMapper().readValue(dataNode.get(dataNodeName).toString(), new TypeReference<>() {
                        });
                    }
                }
            }
        } catch (Exception e) {
            log.error("API 호출 및 응답 처리 중 오류 발생: {}", e.getMessage());
        }

        return dataList;
    }
}
