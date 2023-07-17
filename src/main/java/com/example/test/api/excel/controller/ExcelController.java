package com.example.test.api.excel.controller;

import com.example.test.api.config.Code;
import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.excel.service.ExcelService;
import com.example.test.api.excel.vo.Excel;
import com.example.test.api.excel.vo.ExcelRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/excel")
@Tag(name = "사용자 관련")
public class ExcelController {

    private final ExcelService excelService;
    private final EmpDao empDao;

    /**
     * 엑셀
     * 업로드(읽기)
     */
    @Operation(summary = "엑셀 읽기", description = "엑셀 읽기")
    @GetMapping("/read")
    public ResponseEntity<ExcelRes> readExcel(@RequestParam String filename) {
        List<Excel> excelList = excelService.readExcel(filename);

        ExcelRes excelRes = new ExcelRes();
        excelRes.setExcelList(excelList);

        if (excelList.isEmpty()) {
            excelRes.setCode(Code.FAIL); // 실패
        } else {
            excelRes.setCode(Code.SUCCESS); // 성공
        }

        return ResponseEntity.ok(excelRes);
    }


    /**
     * 엑셀
     * 다운로드
     */
    @Operation(summary = "엑셀 다운로드", description = "엑셀 다운로드")
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadExcel() {
        List<EmpDTO> list = empDao.getEmpList();
        byte[] bytes = excelService.downloadExcel(list);

        ExcelRes excelRes = new ExcelRes();
        excelRes.setEmpList(list);

        if (bytes != null && bytes.length > 0) {
            excelRes.setCode(Code.SUCCESS); // 성공
        } else {
            excelRes.setCode(Code.FAIL); // 실패
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "data.xlsx");

        return ResponseEntity.ok().headers(headers).body(bytes);
    }
}
