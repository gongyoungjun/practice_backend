package com.example.test.api.emp.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Lesson {
    @Schema(description = "이름")
    private String name;
    @Schema(description = "등록일")
    private String registeredDate;
    @Schema(description = "등록 된 수업")
    private int registeredLessons;
    @Schema(description = "받은 수업")
    private int receivedLessons;
    @Schema(description = "남은 수업")
    private int remainingLessons;


}
