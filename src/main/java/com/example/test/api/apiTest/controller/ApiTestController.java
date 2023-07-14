package com.example.test.api.apiTest.controller;

import com.example.test.api.apiTest.dto.ApiTestDTO;
import com.example.test.api.apiTest.service.ApiTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Tag(name = "사용자 관련")
public class ApiTestController {

    private final ApiTestService apiTestService;


    @Operation(summary = "다른 api 호출", description = "다른 api 호출")
    @PostMapping
    public ResponseEntity<List<ApiTestDTO>> apiTestList() {
        // 서비스 클래스의 apiTestList() 메서드를 호출하여 데이터를 가져옴
        List<ApiTestDTO> apiTestList = apiTestService.apiTestList();

        // 데이터를 포함한 ResponseEntity 생성
        return ResponseEntity.ok(apiTestList);
    }

}

