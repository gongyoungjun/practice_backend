package com.example.test.api.emp.vo;

import lombok.Data;

@Data
public class EmpReq extends PageParam {

    private int vctnNo;
    private String vctnStNm;
    private int empNo;
    private String empNm;
    private String empPhn;
    private String empPwd;


}

