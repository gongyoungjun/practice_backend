package com.example.test.emp.vo;

import lombok.Data;

@Data
public class EmpReq extends PageParam {

    private String empNm;
    private String empPhn;


    /**
     * keyword
     * private
     * 외부에서 사용
     */
//    public void setKeyword(String keyword) {
//        this.keyword = keyword;
//
//    }
}
