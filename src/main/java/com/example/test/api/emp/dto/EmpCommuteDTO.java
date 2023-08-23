package com.example.test.api.emp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmpCommuteDTO {

    @Schema(description = "출퇴근 번호")
    private int atndnNo;
    @Schema(description = "사번")
    private int empNo;
    @Schema(description = "사원 이름")
    private String empNm;
    @Schema(description = "현재 위치")
    private String address;
    @Schema(description = "위도 경도")
    private String geoLoc;
    @Schema(description = "시간")
    private LocalDateTime time;
    @Schema(description = "출퇴근 여부")
    private String workCd;

}
