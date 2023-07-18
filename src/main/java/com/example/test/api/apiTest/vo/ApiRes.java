package com.example.test.api.apiTest.vo;

import com.example.test.api.apiTest.dto.ApiLessonDTO;
import com.example.test.api.apiTest.dto.ApiMemberDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApiRes {

    private String status;
    private String code;
    private String message;
    private Object data;

}
