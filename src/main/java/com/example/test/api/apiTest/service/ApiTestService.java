package com.example.test.api.apiTest.service;

import com.example.test.api.apiTest.dto.ApiEmployeesDTO;
import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;

import java.util.List;

public interface ApiTestService {

    /**
     * 회원 목록
     */
    List<ApiMemberDTO> apiTestList();


    /**
     * 회원별 레슨 조회
     */
    List<ApiLessonDTO> lessonMemberView();


    /**
     * 사원 정보 목록
     * 이름
     */
    Object getEmployees(String nmKeyword);

    /**
     * 사원 정보 목록
     * no
     */
    List<ApiEmployeesDTO> detailEmployees(int empNo);

}
