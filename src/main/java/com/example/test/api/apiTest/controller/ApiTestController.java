package com.example.test.api.apiTest.controller;

import com.example.test.api.apiTest.dto.ApiEmployeesDTO;
import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import com.example.test.api.apiTest.service.ApiTestService;
import com.example.test.api.apiTest.vo.ApiReq;
import com.example.test.api.apiTest.vo.ApiRes;
import com.example.test.api.config.Code;
import com.example.test.api.login.vo.EmpListReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     */
    @Operation(summary = "외부 api 회원 목록", description = "외부 api 회원 목록")
    @PostMapping("/members")
    public ResponseEntity<ApiRes> apiTestList(ApiReq req) {
        ApiRes res = new ApiRes();
        String code = Code.SUCCESS;
        List<ApiMemberDTO> list = new ArrayList<>();

        try {
            list = apiTestService.apiTestList(req);
        } catch (Exception e) {
            code = Code.FAIL;
        }

        res.setData(list);
        res.setCode(code);

        return ResponseEntity.ok(res);
    }

    /**
     * 회원별 레슨 조회
     */
    @Operation(summary = "외부 api 레슨 조회", description = "외부 api 레슨 조회")
    @PostMapping("/lessons")
    public ResponseEntity<ApiRes> lessonMemberView(ApiReq req) {
        ApiRes res = new ApiRes();
        String code = Code.SUCCESS;
        List<ApiLessonDTO> lessonList = new ArrayList<>();
        try {
            lessonList = apiTestService.lessonMemberView(req);
        } catch (Exception e) {
            code = Code.FAIL;
        }

        res.setData(lessonList);
        res.setCode(code);

        return ResponseEntity.ok(res);
    }

    /**
     * 사원 정보 목록
     */
    @Operation(summary = "외부 api 사원 정보 목록", description = "외부 api 사원 정보 목록")
    @PostMapping("/emp/list")
    public ResponseEntity<ApiRes> getEmployees(ApiReq req) {

        ApiRes res = new ApiRes();
        String code = Code.SUCCESS;
        List<ApiEmployeesDTO> data = null;
        try {
            data = apiTestService.getEmployees(req);
        } catch (Exception e) {
            code = Code.FAIL;
        }
        res.setData(data);
        res.setCode(code);

        return ResponseEntity.ok(res);
    }

    /**
     * 사원 정보 목록
     * no
     * detail
     */
    @Operation(summary = "외부 api 사원 정보 목록(Object)", description = "외부 api 사원 정보 목록(Object)")
    @PostMapping("/emp/search")
    public ResponseEntity<ApiRes> detailEmployees(int empNo) {
        ApiRes res = new ApiRes();
        String code = Code.SUCCESS;
        ApiEmployeesDTO data = null;
        try {
            data = apiTestService.detailEmployees(empNo);
        } catch (Exception e) {
            code = Code.FAIL;
        }
        res.setData(data);
        res.setCode(code);

        return ResponseEntity.ok(res);
    }



}




