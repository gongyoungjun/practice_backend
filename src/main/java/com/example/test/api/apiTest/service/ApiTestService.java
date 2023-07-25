package com.example.test.api.apiTest.service;

import com.example.test.api.apiTest.dto.ApiEmployeesDTO;
import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import com.example.test.api.apiTest.vo.ApiReq;

import java.util.List;

public interface ApiTestService {

    /**
     * 회원 목록
     */
    List<ApiMemberDTO> apiTestList(ApiReq req);

    /**
     * 회원별 레슨 조회
     */
    List<ApiLessonDTO> lessonMemberView(ApiReq req);

    /**
     * 사원 정보 목록
     * 이름
     */
    List<ApiEmployeesDTO> getEmployees(ApiReq req);

    /**
     * 사원 정보 목록
     * no
     * detail
     */
    ApiEmployeesDTO detailEmployees(int empNo);

}
