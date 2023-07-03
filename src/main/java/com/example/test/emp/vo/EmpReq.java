package com.example.test.emp.vo;

import lombok.Data;

@Data
public class EmpReq extends PageParam {

    private String empNm;
    private String empPhn;
    private String keyword;     //검색 객체에 키워드를 설정하기 위해서

    /**
     * keyword
     * private
     * 외부에서 사용
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;

    }
}
