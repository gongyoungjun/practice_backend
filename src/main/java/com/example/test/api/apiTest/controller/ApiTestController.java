package com.example.test.api.apiTest.controller;

import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
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

    /**
     * 회원 목록
     * @return
     */
    @Operation(summary = "외부 api 회원 목록", description = "외부 api 회원 목록")
    @PostMapping("/members")
    public List<ApiMemberDTO> getMembers() {
        return apiTestService.apiTestList();
    }
/*    public ResponseEntity<List<ApiMemberDTO>> apiTestList() {
        // 서비스 클래스의 apiTestList() 메서드를 호출하여 데이터를 가져옴
        List<ApiMemberDTO> apiTestList = apiTestService.apiTestList();

        // 데이터를 포함한 ResponseEntity 생성
        return ResponseEntity.ok(apiTestList);
    }*/

    /**
     * 회원별 레슨 조회
     * @return
     */
    @Operation(summary = "외부 api 회원별 레슨 조회", description = "외부 api 회원별 레슨 조회")
    @PostMapping("/lessons")
    public List<ApiLessonDTO> getLessons() {
        return apiTestService.lessonMemberView();
    }
/*    public ResponseEntity<List<ApiLessonDTO>> lessonMemberView() {
        List<ApiLessonDTO> apiTestList = apiTestService.lessonMemberView();

        return ResponseEntity.ok(apiTestList);
    }*/

}

