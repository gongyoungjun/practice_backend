package com.example.test.api.excel.controller;

import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.excel.service.ExcelService;
import com.example.test.api.excel.vo.Excel;
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
    public ResponseEntity<List<Excel>> readExcel(@RequestParam String filename) {
        List<Excel> excelList = excelService.readExcel(filename);
        return ResponseEntity.ok(excelList);
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

        HttpHeaders headers = new HttpHeaders();
        //contenttype을 설정
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        //attachment 설정 = 첨부 파일 형태로 다운로드 속성
        headers.setContentDispositionFormData("attachment", "data.xlsx");
        //헤더정보
        //바이트배열 응답본문
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

}
