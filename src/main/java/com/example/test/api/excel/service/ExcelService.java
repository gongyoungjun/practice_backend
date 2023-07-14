package com.example.test.api.excel.service;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.excel.vo.Excel;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ExcelService {



    /**
     * 엑셀
     * 업로드(읽기)
     */
    List<Excel> readExcel(String filename);
    /**
     * 엑셀
     * 다운로드
     */
    List<EmpDTO> downloadExcel(HttpServletResponse res) throws IOException;

}
