package com.example.test.api.emp.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpReq extends PageParam {

    @Schema(description = "휴가 신청 인덱스")
    private int vctnNo;
    @Schema(description = "승인 상태")
    private String vctnStCd;
    @Schema(description = "사번")
    private int empNo;
    @Schema(description = "사원 이름")
    private String empNm;
    @Schema(description = "전화번호")
    private String empPhn;
    @Schema(description = "패스워드")
    private String empPwd;
    @Schema(description = "이메일")
    private String empEml;
    @Schema(description = "휴가 시작 날짜")
    private LocalDate vctnStrDt;
    @Schema(description = "휴가 마지막 날짜")
    private LocalDate vctnEndDt;
    @Schema(description = "신청 날짜")
    private String vctnAplDtm;
    @Schema(description = "휴가 시작 날짜 검색")
    private String strDt;
    @Schema(description = "휴가 종료 날짜 검색")
    private String endDt;
    @Schema(description = "snsKey")
    private String snsKey;

}

