package com.example.test.api.apiTest.service.impl;

import com.example.test.api.apiTest.dto.ApiEmployeesDTO;
import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import com.example.test.api.apiTest.service.ApiTestService;
import com.example.test.api.apiTest.vo.ApiReq;
import com.example.test.api.apiTest.vo.ApiRes;
import com.example.test.api.config.ApiUrl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiTestServiceImpl implements ApiTestService {

    @Value("${api.member.url}")
    private String API_MEMBER_URL;

    @Value("${api.lesson.url}")
    private String API_LESSON_URL;

    @Value("${api.emp.url}")
    private String API_EMP_URL;

    @Value("${api.emp.no.url}")
    private String API_EMP_NO_URL;

    @Value("${auth.token}")
    private String AUTH_TOKEN;


    @Tag(name = "RestTemplate - 회원목록")
    @Override
    public List<ApiMemberDTO> apiTestList() {
        return apiAndGetList(API_MEMBER_URL, "list", HttpMethod.POST);
    }

    @Tag(name = "RestTemplate - 회원별 레슨")
    @Override
    public List<ApiLessonDTO> lessonMemberView() {
        return apiAndGetList(API_LESSON_URL, "lessonList",  HttpMethod.POST);
    }

/*    @Tag(name = "RestTemplate - 사원 목록 - 이름 조회 - list")
    @Override
    public List<ApiEmployeesDTO> getEmployees(String nmKeyword) {
        ApiReq req = new ApiReq();
        req.setNmKeyword(nmKeyword);
        return apiAndGetList(API_EMP_URL, "employeeList", null, req, HttpMethod.POST);
    }*/


    @Tag(name = "RestTemplate - 사원 목록 - 이름 조회 - object")
    @Override
    public Object getEmployees(String nmKeyword) {
        ApiReq req = new ApiReq();
        req.setNmKeyword(nmKeyword);
        return apiAndGetObject(API_EMP_URL, null, req, HttpMethod.POST, Object.class);
    }

    @Tag(name = "RestTemplate - 사원 목록 - no 조회")
    @Override
    public List<ApiEmployeesDTO> detailEmployees(int empNo) {
        String apiUrl = API_EMP_NO_URL + "?empNo=" + empNo;
        return apiAndGetList(apiUrl, "data", HttpMethod.GET);
    }


    // 제네릭 타입 T를 사용하여 리스트 반환 메서드
    // apiUrl = API의 엔드포인트 URL
    @Tag(name = "API 공통부분(list)")
    public <T> List<T> apiAndGetList(String apiUrl, String dataNodeName,HttpMethod httpMethod) {
        List<T> dataList = new ArrayList<>();

        try {

            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 설정
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", AUTH_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // vo 객체를 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            //이름 조회 할때, req 필요.
            //String requestData = objectMapper.writeValueAsString(req);

            // HttpEntity(http 요청) 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            // API 호출 및 응답 수신
            // exchange = HTTP 요청을 보내고 응답을 받는 기능을 수행
            // apiUrl: API의 엔드포인트 URL
            // httpMethod: HTTP 메서드 (GET, POST, PUT 등)
            // requestEntity: HttpEntity 객체 - 요청의 헤더와 본문 데이터
            ResponseEntity<ApiRes> response = restTemplate.exchange(ApiUrl.API_URL + apiUrl, httpMethod, requestEntity, ApiRes.class);
            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // JsonNode = 계층 구조를 표현
                    // valueToTree = JsonNode를 객체로 변환
                    JsonNode dataNode = objectMapper.valueToTree(apiRes.getData());
                    if (dataNode.has(dataNodeName)) {
                        // TypeReference = 제너릭 동적 유지
                        dataList = objectMapper.readValue(dataNode.get(dataNodeName).toString(), new TypeReference<>() {
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }

    @Tag(name = "API 공통부분(object)")
    public <T> T apiAndGetObject(String apiUrl, Map<String, String> addHeaders, ApiReq req, HttpMethod httpMethod, Class<T> responseType) {
        T dataObject = null;

        try {
            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", AUTH_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 추가적인 헤더가 있는 경우 이를 HttpHeaders에 추가합니다.
            if (addHeaders != null) {
                for (Map.Entry<String, String> entry : addHeaders.entrySet()) {
                    headers.set(entry.getKey(), entry.getValue());
                }
            }

            // vo 객체를 JSON 문자열로 변환
            //writeValueAsString - string 변환 대상
            ObjectMapper objectMapper = new ObjectMapper();
            String requestData = objectMapper.writeValueAsString(req);

            // HttpEntity(http 요청) 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = restTemplate.exchange(ApiUrl.API_URL + apiUrl, httpMethod, requestEntity, ApiRes.class);
            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // 단일 오브젝트로 변환
                    dataObject = objectMapper.convertValue(apiRes.getData(), responseType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataObject;
    }

}
