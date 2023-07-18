package com.example.test.api.apiTest.controller;

import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import com.example.test.api.apiTest.service.ApiTestService;
import com.example.test.api.apiTest.vo.ApiRes;
import com.example.test.api.config.Code;
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
     *
     * @return
     */
    @Operation(summary = "외부 api 회원 목록", description = "외부 api 회원 목록")
    @PostMapping("/members")
    public ResponseEntity<ApiRes> getMembers() {
        ApiRes res = new ApiRes();
        String code = Code.SUCCESS;
        List<ApiMemberDTO> list = null;
        try {
            list = apiTestService.apiTestList();
        } catch (Exception e) {
            code = Code.FAIL;
        }
        res.setData(list);
        res.setCode(code);

        return ResponseEntity.ok(res);
    }


    /**
     * 회원별 레슨 조회
     *
     * @return
     */
    @Operation(summary = "외부 api 회원별 레슨 조회", description = "외부 api 회원별 레슨 조회")
    @PostMapping("/lessons")
    public ResponseEntity<ApiRes> getLessons() {
        ApiRes res = new ApiRes();
        String code = Code.SUCCESS;
        List<ApiLessonDTO> list = null;
        try {
            list = apiTestService.lessonMemberView();
        } catch (Exception e) {
            code = Code.FAIL;
        }
        res.setData(list);
        res.setCode(code);

        return ResponseEntity.ok(res);
    }
}
