package com.example.test.api.apiTest.vo;

import lombok.Data;

@Data
public class ApiReq {
    private int offset;
    private int empNo;
    private int cntPerPage;
    private int mbrNo;
    private String mbrNm;
    private String mbrStCd;
    private String nmKeyword;

}
