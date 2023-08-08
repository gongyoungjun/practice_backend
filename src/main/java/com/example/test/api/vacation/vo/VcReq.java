package com.example.test.api.vacation.vo;

import com.example.test.api.emp.vo.PageParam;
import lombok.Data;

@Data
public class VcReq extends PageParam {

    private int vctnNo;
    private String empNm;
    private int empNo;
    private String vctnStCd;


}
