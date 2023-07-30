package com.example.test.api.vacation.vo;

import com.example.test.api.emp.vo.PageParam;
import lombok.Data;

@Data
public class VcReq extends PageParam {

    private int vctnNo;
    private int empNo;
    private String vctnStNm;


}
