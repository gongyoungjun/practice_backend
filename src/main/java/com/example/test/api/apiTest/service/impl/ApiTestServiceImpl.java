package com.example.test.api.apiTest.service.impl;

import com.example.test.api.apiTest.dto.ApiTestDTO;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiTestServiceImpl implements ApiTestService {

    /*@Tag(name = "HttpURLConnection 사용")
    @Override
    public List<ApiTestDTO> apiTestList() {
        List<ApiTestDTO> dataList = new ArrayList<>();

        try {
            // API 엔드포인트 URL 설정
            URL url = new URL("http://mjgolf.myqnapcloud.com/api/admin/memberList");

            // HttpURLConnection 객체 생성(웹서버와 통신 클래스)
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // 헤더 추가
            conn.setRequestProperty("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODQ4MTA4OTAsInN1YiI6IjExMiIsImV4cCI6MTcxNjM0Njg5MH0.r98D7N7cdpoCVKpW_bUVTOXwHllgt83toirt_E6nCH8");
            conn.setRequestProperty("Content-Type", "application/json");

            // 요청 본문 데이터 추가
            String requestData = "{}"; // 요청 본문에 실제 데이터 추가하여 구성
            conn.setDoOutput(true);
            //출력 얻고 데이터 전송
            //StandardCharsets = 표준문자 인코딩 정의 상수
            //flush 모든 데이터 내보냄(버퍼를 비움)
            try (OutputStream outputStream = conn.getOutputStream()) {
                outputStream.write(requestData.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 응답 데이터 읽기(BufferedReader)
                // StringBuilder = 스트링 조작
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 응답 데이터 처리
                String responseData = response.toString();

                // 응답 데이터 파싱
                //jsonnode = 키-값으로구성 -> 데이터 표현, 조작
                ObjectMapper objectMapper = new ObjectMapper(); // 객체 생성
                JsonNode rootNode = objectMapper.readTree(responseData); //readtree = json 문자열을 객체 변환
                if (rootNode.has("data")) { //data 필드 확인
                    JsonNode dataNode = rootNode.get("data"); //해당 필드 값 가져오기
                    if (dataNode.has("list")) { //list 필드 확인
                        JsonNode listNode = dataNode.get("list"); // 필드값 가져오기

                        // readValue = 문자열을 지정된 클래스 타입 변환 해당 객체 변환
                        // toString =  객체를 문자열 변환 메서드
                        // TypeReference = 전달되는 매개변수 List<ApiTestDTO> 타입으로 변환
                        dataList = objectMapper.readValue(listNode.toString(), new TypeReference<>() {
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }*/

    @Tag(name = "RestTemplate 사용")
    @Override
    public List<ApiTestDTO> apiTestList() {
        List<ApiTestDTO> dataList = new ArrayList<>();

        try {
            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // API 엔드포인트 URL 설정
            String apiUrl = "http://mjgolf.myqnapcloud.com/api/admin/memberList";

            // HttpHeaders 설정
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODQ4MTA4OTAsInN1YiI6IjExMiIsImV4cCI6MTcxNjM0Njg5MH0.r98D7N7cdpoCVKpW_bUVTOXwHllgt83toirt_E6nCH8");
            headers.setContentType(MediaType.APPLICATION_JSON); //Content-Type - application/json 로 이해

            // 요청 본문 데이터 생성
            String requestData = "{}"; // 요청 본문 데이터 설정

            // HttpEntity(http요청) 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

            // API 호출 및 응답 수신
            // exchange = HTTP 요청을 보내고 응답을 받는 기능을 수행
            // apiUrl API의 엔드포인트 URL
            // requestEntity: HttpEntity 객체 - 요청의 헤더와 본문 데이터
            ResponseEntity<ApiRes> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, ApiRes.class);
            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    JsonNode dataNode = new ObjectMapper().valueToTree(apiRes.getData());
                    if (dataNode.has("list")) {
                        dataList = new ObjectMapper().readValue(dataNode.get("list").toString(), new TypeReference<>() {
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }
}
