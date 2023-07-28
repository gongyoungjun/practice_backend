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

    /**
     * 잔여 휴가일 계산
     */
    @Schema(description = "총 휴가일 수")
    private int empVctnTtl;
    @Schema(description = "반차 신청일 수")
    private BigDecimal vctnHalfCnt;
    @Schema(description = "휴가 신청일 수")
    private BigDecimal vctnDayCnt;
    @Schema(description = "잔여 휴가")
    private BigDecimal vctnRsdCnt;
    @Schema(description = "신청 날짜")
    private String vctnAplDtm;
}
