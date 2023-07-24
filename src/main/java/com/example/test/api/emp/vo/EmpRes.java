package com.example.test.api.emp.vo;

import com.example.test.api.emp.dto.EmpDTO;
import lombok.Data;

import java.util.List;

@Data
public class EmpRes extends PageParam {

    private String query;
    private Object data;
    private String code;
    private int startList;

    private List<EmpDTO> list;
}
