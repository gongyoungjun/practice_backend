package com.example.test.api.emp.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpReq extends PageParam {

    private int vctnNo;
    private String vctnStCd;
    private int empNo;
    private String empNm;
    private String empPhn;
    private String empPwd;
    private LocalDate vctnStrDt;
    private LocalDate vctnEndDt;
    private String vctnAplDtm;


}

