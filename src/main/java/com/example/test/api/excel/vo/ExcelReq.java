package com.example.test.api.excel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ExcelReq {

    @Schema(description = "사번")
    private int empNo;
    @Schema(description = "사원이름")
    private String empNm;
    @Schema(description = "전화번호")
    private String empPhn;
    @Schema(description = "이메일")
    private String empEml;
    @Schema(description = "파일이름")
    private String filename;
}
