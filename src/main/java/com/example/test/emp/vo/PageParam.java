package com.example.test.emp.vo;

import com.example.test.emp.dto.EmpDTO;
import lombok.Data;

import java.util.List;

@Data
public class PageParam {

    private int listSize = 4;                //초기값으로 목록개수를 10으로 셋팅
    private int page = 1;
    private int listCnt;
    private int startList;

    public int getStartList() {
        return (page - 1) * listSize;
    }

    private List<EmpDTO> empList;
}
