package com.example.test.api.excel.service;

import com.example.test.api.excel.vo.Excel;

import java.util.List;

public interface ExcelService {

    /**
     * 엑셀
     * 업로드(읽기)
     */
    // readExcel(String filename);
    List<Excel> readExcel(String filename);
    /**
     * 엑셀
     * 다운로드
     */
    int downloadExcel(String filename);


}
