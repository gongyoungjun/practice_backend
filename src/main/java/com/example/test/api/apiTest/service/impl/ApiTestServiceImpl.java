package com.example.test.api.apiTest.service.impl;

import com.example.test.api.apiTest.dto.ApiEmployeesDTO;
import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import com.example.test.api.apiTest.service.ApiTestService;
import com.example.test.api.apiTest.vo.ApiReq;
import com.example.test.api.apiTest.vo.ApiRes;
import com.example.test.api.apiTest.vo.TestReq;
import com.example.test.api.config.ApiUrl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<ApiMemberDTO> apiTestList(ApiReq req) {
        return apiAndGetList(API_MEMBER_URL, req, "list", HttpMethod.POST);
    }

    @Tag(name = "RestTemplate - 레슨리스트")
    @Override
    public List<ApiLessonDTO> lessonMemberView(ApiReq req) {



        return apiAndGetList(API_LESSON_URL, req, "lessonList", HttpMethod.POST);
    }

    @Tag(name = "RestTemplate - 사원 목록 - 이름 조회 - list")
    @Override
    public List<ApiEmployeesDTO> getEmployees(ApiReq req) {
        return apiAndGetList(API_EMP_URL, req, "employeeList", HttpMethod.POST);
    }

/*    @Tag(name = "RestTemplate - 사원 목록 - no로 가져오기")
    @Override1
    public ApiEmployeesDTO detailEmployees(int empNo) {
        ApiReq req = new ApiReq();
        req.setEmpNo(empNo);
        ApiEmployeesDTO res = apiAndGetObject(API_EMP_NO_URL, req,HttpMethod.GET , null);
        return res;
    }*/

    @Tag(name = "RestTemplate - 사원 목록 - no 조회")
    @Override
    public ApiEmployeesDTO detailEmployees(int empNo) {
        TestReq req = new TestReq();
        req.setEmpNo(empNo);
        return apiAndGetObject(API_EMP_NO_URL, req, HttpMethod.GET);
    }


    // 제네릭 타입 T를 사용하여 리스트 반환 메서드
    // apiUrl = API의 엔드포인트 URL
    // exchange = HTTP 헤더를 새로 만들 수 있고 어떤 HTTP 메서드도 사용가능하다
    @Tag(name = "API 공통부분(list)")
    public <T> List<T> apiAndGetList(String apiUrl, ApiReq req, String dataNodeName, HttpMethod httpMethod) {
        List<T> dataList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 요청 데이터를 JSON으로 변환
            String requestData = objectMapper.writeValueAsString(req);
            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = this.apiExchange(apiUrl, requestData, httpMethod);
            // API 호출 및 응답 수신
            // exchange = HTTP 요청을 보내고 응답을 받는 기능을 수행
            // apiUrl: API의 엔드포인트 URL
            // httpMethod: HTTP 메서드 (GET, POST, PUT 등)
            // requestEntity: HttpEntity 객체 - 요청의 헤더와 본문 데이터
            // getStatusCodeValue() = 응답 상태 코드값
            //3 응답객채 파싱
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // 데이터 노드 이름에 해당하는 값 추출
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
    public <T> T apiAndGetObject(String apiUrl, ApiReq req, HttpMethod httpMethod) {
        T dataObject = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 요청 데이터를 JSON으로 변환
            String requestData = objectMapper.writeValueAsString(req);
            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = this.apiExchange(apiUrl, requestData, httpMethod);

            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // 데이터가 객체인 경우 responseType에 지정된 클래스로 변환
                    dataObject = objectMapper.convertValue(apiRes.getData(), new TypeReference<>() {
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataObject;
    }




    @Tag(name = "API 공통부분")
    public ResponseEntity<ApiRes> apiExchange(String apiUrl, Map<String, String> params) {
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", AUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // UriComponentsBuilder 파라미터
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ApiUrl.API_URL + apiUrl);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        // HttpEntity(http 요청) 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // API 호출 및 응답 수신
        ResponseEntity<ApiRes> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, ApiRes.class);
        return response;
    }



}

/*    // 제네릭 타입 T를 사용하여 리스트 반환 메서드
    // apiUrl = API의 엔드포인트 URL
    @Tag(name = "API 공통부분(list)")
    public <T> List<T> apiAndGetList(String apiUrl, ApiReq req, String dataNodeName, HttpMethod httpMethod) {
        List<T> dataList = new ArrayList<>();

        try {

            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 설정
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", AUTH_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);


            // vo 객체를 JSON 문자열로 변환합니다.
            ObjectMapper objectMapper = new ObjectMapper();
            String requestData = objectMapper.writeValueAsString(req);

            // HttpEntity(http 요청) 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

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
    public <T> T apiAndGetObject(String apiUrl, ApiReq req,String dataNodeName, HttpMethod httpMethod, Class<T> responseType) {
        T dataObject = null;

        try {
            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 설정
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", AUTH_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // vo 객체를 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String requestData = objectMapper.writeValueAsString(req);

            // HttpEntity(http 요청) 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

            // API 호출 및 응답 수신
            ResponseEntity<T> response = restTemplate.exchange(ApiUrl.API_URL + apiUrl, httpMethod, requestEntity, responseType);

            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                dataObject = response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataObject;
    }

}*/