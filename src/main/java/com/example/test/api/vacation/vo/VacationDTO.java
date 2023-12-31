package com.example.test.api.vacation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VacationDTO {

    @Schema(description = "휴가 신청 인덱스")
    private int vctnNo;
    @Schema(description = "휴가 사유")
    private String vctnRsn;
    @Schema(description = "사번")
    private int empNo;
    @Schema(description = "사원 이름")
    private String empNm;
    @Schema(description = "휴가 시작 날짜")
    private LocalDate vctnStrDt;
    @Schema(description = "휴가 마지막 날짜")
    private LocalDate vctnEndDt;
    @Schema(description = "승인 상태")
    private String vctnStCd;
    @Schema(description = "휴가 시작 날짜 검색")
    private String strDt;
    @Schema(description = "휴가 종료 날짜 검색")
    private String endDt;
    /**
     * 잔여 휴가일 계산
     */
    @Schema(description = "총 휴가일 수")
    private int empVctnTtl;
    @Schema(description = "휴가 종류 코드")
    private String vctnKndCd;
    @Schema(description = "반차 신청일 수")
    private BigDecimal vctnHalfCnt;
    @Schema(description = "휴가 신청일 수")
    private BigDecimal vctnDayCnt;
    @Schema(description = "잔여 휴가")
    private BigDecimal vctnRsdCnt;
    @Schema(description = "신청 날짜")
    private String vctnAplDtm;
    @Schema(description = "휴가 종류")
    private String vctnKndNm;
    @Schema(description = "승인 상태")
    private String vctnStNm;



}
