package com.example.test.api.excel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Excel {

    @Schema(description = "번호")
    private String num;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "레슨 등록")
    private int registeredLessons;
    @Schema(description = "레슨 진행")
    private String receivedLessons;
    @Schema(description = "등록 일시")
    private String registeredDate;


}
