package com.example.test.api.apiTest.vo;

import com.example.test.api.apiTest.dto.ApiTestDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApiRes {

    private Object data;
    private List<ApiTestDTO> list;


}
