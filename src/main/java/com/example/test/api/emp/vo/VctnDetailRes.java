package com.example.test.api.emp.vo;

import com.example.test.api.apiTest.vo.ApiRes;
import com.example.test.api.vacation.vo.VacationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VctnDetailRes extends ApiRes {
    @Schema(description = "응답")
    public VacationDTO data;
}
