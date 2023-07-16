package com.example.test.api.apiTest.service.impl;

import com.example.test.api.apiTest.dto.ApiTestDTO;
import com.example.test.api.apiTest.service.ApiTestService;
import com.example.test.api.apiTest.vo.ApiRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiTestServiceImpl implements ApiTestService {



/*    @Override
    public List<ApiTestDTO> apiTestList() {
        List<ApiTestDTO> dataList = new ArrayList<>();

        try {
            // API 엔드포인트 URL 설정
            URL url = new URL("http://mjgolf.myqnapcloud.com/api/admin/memberList");

            // HttpURLConnection 객체 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // 헤더 추가
            conn.setRequestProperty("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODQ4MTA4OTAsInN1YiI6IjExMiIsImV4cCI6MTcxNjM0Njg5MH0.r98D7N7cdpoCVKpW_bUVTOXwHllgt83toirt_E6nCH8");
            conn.setRequestProperty("Content-Type", "application/json");
            // 필요한 경우 다른 헤더도 추가할 수 있습니다.

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 응답 데이터 읽기
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
                ObjectMapper objectMapper = new ObjectMapper();
                ApiRes apiRes = objectMapper.readValue(responseData, ApiRes.class);
                dataList = apiRes.getList(); // 데이터 리스트만 저장

            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }*/

}
