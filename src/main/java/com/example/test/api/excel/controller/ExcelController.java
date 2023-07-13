package com.example.test.api.excel.controller;

import com.example.test.api.config.Code;
import com.example.test.api.excel.service.ExcelService;
import com.example.test.api.excel.vo.Excel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 엑셀
     * 업로드(읽기)
     */
    @Operation(summary = "엑셀 읽기", description = "엑셀 읽기")
    @GetMapping("/read")
    public ResponseEntity<List<Excel>> readExcel(@RequestParam String filename) {
        String code = Code.FAIL;

        List<Excel> excelList = excelService.readExcel(filename);

        if (excelList !=null) {
            code = Code.SUCCESS;
        }
        return ResponseEntity.ok(excelList);
    }


    /**
     * 엑셀
     * 다운로드
     */
    @Operation(summary = "엑셀 다운로드", description = "엑셀 다운로드")
    @GetMapping("/download")
    public ResponseEntity<String> downloadExcel(@RequestParam String filename) {
        String code = Code.FAIL;

        int result = excelService.downloadExcel(filename);

        if (result > 0) {
            code = Code.SUCCESS;
        }

        return ResponseEntity.ok(code);
    }

}
