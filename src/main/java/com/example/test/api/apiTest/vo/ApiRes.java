package com.example.test.api.apiTest.vo;

import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApiRes {

    private Object data;
    private List<ApiMemberDTO> memberList;

    private List<ApiLessonDTO> lessonList;

}
