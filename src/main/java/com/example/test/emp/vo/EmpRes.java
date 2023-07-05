package com.example.test.emp.vo;

import com.example.test.emp.dto.EmpDTO;
import lombok.Data;

import java.util.List;

@Data
public class EmpRes extends PageParam {

    private String query;
    private String data;
    private int startList;

    private List<EmpDTO> list;
}
