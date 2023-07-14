package com.example.test.api.excel.vo;

import com.example.test.api.emp.dto.EmpDTO;
import lombok.Data;

import java.util.List;

@Data
public class ExcelRes {

    private List<EmpDTO> list;
}
