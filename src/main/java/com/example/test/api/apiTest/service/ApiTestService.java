package com.example.test.api.apiTest.service;

import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;

import java.util.List;

public interface ApiTestService {

    /**
     * 회원 목록
     * @return
     */
    List<ApiMemberDTO> apiTestList();


    /**
     * 회원별 레슨 조회
     * @return
     */
    List<ApiLessonDTO> lessonMemberView();

}
