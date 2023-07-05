package com.example.test.emp.vo;

import lombok.Data;

@Data
public class EmpReq extends PageParam {

    private int empNo;
    private String empNm;
    private String empPhn;
}

