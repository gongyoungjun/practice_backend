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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    public List<ApiMemberDTO> apiTestList(ApiReq req) {
/*        return apiAndGetList(API_MEMBER_URL, req, "list", HttpMethod.POST);*/
        return apiAndGetList(API_MEMBER_URL, req, "list");
    }

    @Tag(name = "RestTemplate - 레슨리스트")
    @Override
    public List<ApiLessonDTO> lessonMemberView(ApiReq req) {


        return apiAndGetList(API_LESSON_URL, req, "lessonList");
    }

    @Tag(name = "RestTemplate - 사원 목록 - 이름 조회 - list")
    @Override
    public List<ApiEmployeesDTO> getEmployees(ApiReq req) {
        return apiAndGetList(API_EMP_URL, req, "employeeList");
    }

    @Tag(name = "RestTemplate - 사원 목록 - no 조회")
    @Override
    public ApiEmployeesDTO detailEmployees(int empNo) {
        ApiReq req = new ApiReq();
        req.setEmpNo(empNo);
        ApiEmployeesDTO res = apiAndGetObject(API_EMP_NO_URL, req);
        return res;

    }



    // 제네릭 타입 T를 사용하여 리스트 반환 메서드
    // apiUrl = API의 엔드포인트 URL
    // exchange = HTTP 헤더를 새로 만들 수 있고 어떤 HTTP 메서드도 사용가능하다
    @Tag(name = "API 공통부분")
    private <T> ResponseEntity<T> apiExchange(String apiUrl, HttpMethod httpMethod, Class<T> responseType) {
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", AUTH_TOKEN); // AUTH_TOKEN은 클래스에 선언되어 있는 상수 또는 변수로 가정합니다.
        headers.setContentType(MediaType.APPLICATION_JSON);

        // UriComponentsBuilder를 사용하여 외부 API의 URL을 구성합니다.
        // ApiUrl.API_URL + apiUrl는 외부 API의 기본 URL과 요청 API 엔드포인트를 합칩니다.
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ApiUrl.API_URL + apiUrl);

        // HttpEntity(http 요청) 생성 - 요청 데이터는 null로 설정
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        // restTemplate을 사용하여 외부 API에 요청을 보내고 응답을 받습니다.
        ResponseEntity<T> response;
        try {
            response = restTemplate.exchange(builder.toUriString(), httpMethod, requestEntity, responseType);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @Tag(name = "API 공통부분(list)")
    public <T> List<T> apiAndGetList(String apiUrl, ApiReq req, String dataNodeName) {
        List<T> dataList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 요청 데이터를 JSON으로 변환
            String requestData = objectMapper.writeValueAsString(req);

            // UriComponentsBuilder를 사용하여 요청 파라미터를 URL에 추가
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ApiUrl.API_URL + apiUrl)
                    .queryParam("requestData", requestData);

            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = this.apiExchange(builder.toUriString(), HttpMethod.GET, ApiRes.class);

            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // 데이터 노드 이름에 해당하는 값 추출
                    JsonNode dataNode = objectMapper.valueToTree(apiRes.getData());
                    if (dataNode.has(dataNodeName)) {
                        // TypeReference = 제너릭 동적 유지
                        dataList = objectMapper.readValue(dataNode.get(dataNodeName).toString(), new TypeReference<>() {});
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }

    @Tag(name = "API 공통부분(object)")
    public <T> T apiAndGetObject(String apiUrl, ApiReq req) {
        T dataObject = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 요청 데이터를 JSON으로 변환
            String requestData = objectMapper.writeValueAsString(req);

            // UriComponentsBuilder를 사용하여 요청 파라미터를 URL에 추가
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ApiUrl.API_URL + apiUrl)
                    .queryParam("requestData", requestData);

            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = this.apiExchange(builder.toUriString(), HttpMethod.GET, ApiRes.class);

            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // 데이터가 객체인 경우 responseType에 지정된 클래스로 변환
                    dataObject = objectMapper.convertValue(apiRes.getData(), new TypeReference<>() {});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataObject;
    }

   /* @Tag(name = "API 공통부분")
    private ResponseEntity<ApiRes> apiExchange(String apiUrl, String requestData, HttpMethod httpMethod) {

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", AUTH_TOKEN); // AUTH_TOKEN은 클래스에 선언되어 있는 상수 또는 변수로 가정합니다.
        headers.setContentType(MediaType.APPLICATION_JSON);

        // UriComponentsBuilder를 사용하여 외부 API의 URL을 구성합니다.
        // ApiUrl.API_URL + apiUrl는 외부 API의 기본 URL과 요청 API 엔드포인트를 합칩니다.
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ApiUrl.API_URL + apiUrl);

        // HttpEntity(http 요청) 생성
        // requestData는 요청 데이터(JSON 형식)를 가리킵니다.
        HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

        // restTemplate을 사용하여 외부 API에 요청을 보내고 응답을 받습니다.
        // httpMethod는 HTTP 메서드(GET, POST, PUT 등)를 나타냅니다.
        // ApiRes.class는 외부 API의 응답을 처리하기 위한 클래스로 가정합니다.
        ResponseEntity<ApiRes> response;
        try {
            response = restTemplate.exchange(builder.toUriString(), httpMethod, requestEntity, ApiRes.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }*/
/*   @Tag(name = "API 공통부분 - POST")
   public <T> ResponseEntity<ApiRes<T>> apiPostRequest(String apiUrl, TestReq req) {

       // RestTemplate 객체 생성
       RestTemplate restTemplate = new RestTemplate();

       HttpHeaders headers = new HttpHeaders();
       headers.set("Authorization", AUTH_TOKEN);
       headers.setContentType(MediaType.APPLICATION_JSON);

       ObjectMapper objectMapper = new ObjectMapper();
       String requestData;
       try {
           requestData = objectMapper.writeValueAsString(req);
       } catch (JsonProcessingException e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

       try {
           ResponseEntity<ApiRes<T>> response = restTemplate.exchange(
                   ApiUrl.API_URL + apiUrl,
                   HttpMethod.POST,
                   requestEntity,
                   new ParameterizedTypeReference<>() {
                   }
           );
           return response;
       } catch (HttpClientErrorException e) {
           return new ResponseEntity<>(e.getStatusCode());
       }
   }

    @Tag(name = "API 공통부분 - GET")
    public <T> ResponseEntity<ApiRes<T>> apiGetRequest(String apiUrl, Map<String, String> params) {

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", AUTH_TOKEN);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiRes> response = restTemplate.exchange(
                    UriComponentsBuilder.fromUriString(ApiUrl.API_URL + apiUrl)
                            .queryParams(params)
                            .build()
                            .toUri(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return response;
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }*/
}