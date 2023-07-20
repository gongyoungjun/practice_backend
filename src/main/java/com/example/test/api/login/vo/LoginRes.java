package com.example.test.api.login.vo;

import com.example.test.api.emp.dto.EmpDTO;
import lombok.Data;

import java.util.List;

@Data
public class LoginRes {

    private String code;
    private List<EmpDTO> data;
    private String message;


}
