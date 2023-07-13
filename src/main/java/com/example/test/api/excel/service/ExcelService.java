package com.example.test.api.excel.service;

public interface ExcelService {

    /**
     * 엑셀
     * 업로드(읽기)
     */
    int readExcel(String filename);

    /**
     * 엑셀
     * 다운로드
     */
    int downloadExcel(String filename);


}
