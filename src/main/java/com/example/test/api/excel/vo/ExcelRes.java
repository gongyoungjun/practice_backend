package com.example.test.api.excel.vo;

import com.example.test.api.emp.dto.EmpDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ExcelRes {

    @Schema(description = "파일이름")
    private String filename;
    @Schema(description = "에러메세지")
    private String code;

    private List<EmpDTO> empList;
    private List<Excel> excelList;

}

