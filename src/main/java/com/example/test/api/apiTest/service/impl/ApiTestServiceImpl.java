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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

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
        return apiAndGetList(API_MEMBER_URL, req, "list", HttpMethod.POST,ApiMemberDTO.class);
    }

    @Tag(name = "RestTemplate - 레슨리스트")
    @Override
    public List<ApiLessonDTO> lessonMemberView(ApiReq req) {


        return apiAndGetList(API_LESSON_URL, req, "lessonList", HttpMethod.POST, ApiLessonDTO.class);
    }

    @Tag(name = "RestTemplate - 사원 목록 - 이름 조회 - list")
    @Override
    public List<ApiEmployeesDTO> getEmployees(ApiReq req) {
        return apiAndGetList(ApiUrl.API_URL + API_EMP_URL, req, "employeeList", HttpMethod.POST,ApiEmployeesDTO.class);
    }

    @Tag(name = "RestTemplate - 사원 목록 - no 조회")
    @Override
    public ApiEmployeesDTO detailEmployees(int empNo) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ApiUrl.API_URL + API_EMP_NO_URL)
                .queryParam("empNo", empNo);

        ApiEmployeesDTO res = apiAndGetObject(builder.toUriString(), HttpMethod.GET, ApiEmployeesDTO.class);
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

        // HttpEntity(http 요청) 생성 - 요청 데이터는 null로 설정
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        // restTemplate을 사용하여 외부 API에 요청을 보내고 응답을 받습니다.
        ResponseEntity<T> response;
        try {
            response = restTemplate.exchange(apiUrl, httpMethod, requestEntity, responseType);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }


    @Tag(name = "API 공통부분(list)")
    public <T> List<T> apiAndGetList(String apiUrl, ApiReq req, String dataNodeName, HttpMethod httpMethod, Class<T> responseType) {
        List<T> dataList = new ArrayList<>();

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 요청 데이터를 JSON으로 변환
            String requestData = objectMapper.writeValueAsString(req);

            // HttpHeaders 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", AUTH_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HttpEntity 생성 - 요청 데이터를 body로 설정
            HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = restTemplate.exchange(ApiUrl.API_URL + apiUrl, httpMethod, requestEntity, ApiRes.class);

            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // 데이터 노드 이름에 해당하는 값 추출
                    JsonNode dataNode = objectMapper.valueToTree(apiRes.getData());
                    if (dataNode.has(dataNodeName)) {
                        // TypeReference를 사용
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
    public <T> T apiAndGetObject(String apiUrl, HttpMethod httpMethod, Class<T> responseType) {
        T dataObject = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = this.apiExchange(apiUrl, httpMethod, ApiRes.class);

            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // 데이터가 배열인지 확인
                    if (apiRes.getData() instanceof List) {
                        // 배열인 경우
                        List<?> dataList = (List<?>) apiRes.getData();
                        if (!dataList.isEmpty()) {
                            // 배열이 비어있지 않은 경우, 첫 번째 요소를 responseType에 지정된 클래스로 변환
                            dataObject = objectMapper.convertValue(dataList.get(0), responseType);
                        }
                    } else {
                        // 배열이 아닌 경우 그대로 responseType에 지정된 클래스로 변환
                        dataObject = objectMapper.convertValue(apiRes.getData(), responseType);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataObject;
    }

    /*
    @Tag(name = "API 공통부분(object)")
    public <T> T apiAndGetObject(String apiUrl, HttpMethod httpMethod, Class<T> responseType) {
        T dataObject = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 요청 데이터를 JSON으로 변환

            // API 호출 및 응답 수신
            ResponseEntity<ApiRes> response = this.apiExchange(apiUrl, httpMethod, ApiRes.class);

            // getStatusCodeValue() = 응답 상태 코드값
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                // 응답 데이터 처리
                ApiRes apiRes = response.getBody();
                if (apiRes != null && apiRes.getData() != null) {
                    // 데이터가 객체인 경우 responseType에 지정된 클래스로 변환
                    dataObject = objectMapper.convertValue(apiRes.getData(), responseType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataObject;
    }*/

}