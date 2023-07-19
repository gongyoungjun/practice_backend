package com.example.test.api.apiTest.service.impl;

import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import com.example.test.api.apiTest.service.ApiTestService;
import com.example.test.api.apiTest.vo.ApiRes;
import com.example.test.api.config.ApiUrl;
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

//    Config config = new Config();

    /*
            try {
                prop.load(new FileInputStream("config.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // URL 및 인증 토큰 가져오기
            String apiMemberUrl = config.getProperty("api.member.url");
            String apiLessonUrl = config.getProperty("api.lesson.url");
            String authToken = config.getProperty("auth.token");
*/

    private static final String API_MEMBER_URL = "/admin/memberList";
    private static final String API_LESSON_URL = "/lesson/list";
    private static final String AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODQ4MTA4OTAsInN1YiI6IjExMiIsImV4cCI6MTcxNjM0Njg5MH0.r98D7N7cdpoCVKpW_bUVTOXwHllgt83toirt_E6nCH8";
//1
//    상세조회 api 개발
//   emp/detailUser
//   empNo=127

//2
    //2순위
    // dto -> map

    //1순위
    // dto ->string json

    //HttpEntity에 vo 요청데이터 넣을수있도록
//        HttpEntity<>(requestData, headers)
    @Tag(name = "RestTemplate - 회원목록")
    @Override
    public List<ApiMemberDTO> apiTestList() {

        return this.apiAndGetList(API_MEMBER_URL, "list");
    }

    @Tag(name = "RestTemplate - 회원별 레슨")
    @Override
    public List<ApiLessonDTO> lessonMemberView() {
        String requestBody = "{}"; // 요청 본문 데이터 설정
        return apiAndGetObject(API_LESSON_URL, List.class, requestBody);
    }



    //제네릭 타입 T를 사용 리스트 반환 메서드
    //apiUrl = API의 엔드포인트 URL
    @Tag(name = "API 공통부분")
    public <T> List<T> apiAndGetList(String apiUrl, String dataNodeName) { //+, Map map
        List<T> dataList = new ArrayList<>();

        try {
            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 설정
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", AUTH_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            //map for each
            // 요청 본문 데이터 생성
            String requestData = "{}"; // 요청 본문 데이터 설정

/*            // Body set
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            String imageFileString = getBase64String(file);
            body.add("filename", fileName);
            body.add("image", imageFileString);*/

            // HttpEntity(http요청) 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

            // API 호출 및 응답 수신
            // exchange = HTTP 요청을 보내고 응답을 받는 기능을 수행
            // apiUrl API의 엔드포인트 URL
            // requestEntity: HttpEntity 객체 - 요청의 헤더와 본문 데이터
            ResponseEntity<ApiRes> response = restTemplate.exchange(ApiUrl.API_URL + apiUrl, HttpMethod.POST, requestEntity, ApiRes.class);
            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // JsonNode = 계층 구조를 표현
                    // valueToTree = JsonNode를 객체로 변환
                    JsonNode dataNode = new ObjectMapper().valueToTree(apiRes.getData());
                    if (dataNode.has(dataNodeName)) {
                        // TypeReferenc = 제너릭 동적 유지
                        dataList = new ObjectMapper().readValue(dataNode.get(dataNodeName).toString(), new TypeReference<>() {
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }


    public <T> T apiAndGetObject(String apiUrl, Class<T> responseType,String requestBody) {
        T object = null;

        try {
            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", AUTH_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // POST로 보내는 경우 : body에 실어보낼 json데이터 생성

            // HttpEntity(http 요청) 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // API 호출 및 응답 수신
            ResponseEntity<String> response = restTemplate.exchange(ApiUrl.API_URL + apiUrl, HttpMethod.POST, requestEntity, String.class);

            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // JSON 문자열을 ApiLessonDTO 객체로 변환
                String responseBody = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                object = objectMapper.readValue(responseBody, responseType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }



}
