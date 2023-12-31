package com.example.test.api.emp.vo;

import lombok.Data;

@Data
public class PageParam {

    private int listSize = 4;                //초기값으로 목록개수를 10으로 셋팅
    private int page = 1;
    private int total;
    private int startList;


    public int getStartList() {
        return (page - 1) * listSize;
    }

}
