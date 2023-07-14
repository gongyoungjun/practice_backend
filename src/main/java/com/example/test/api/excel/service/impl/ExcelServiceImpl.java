package com.example.test.api.excel.service.impl;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.excel.service.ExcelService;
import com.example.test.api.excel.vo.Excel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/static/excel/";


    /**
     * 엑셀
     * 업로드(읽기)
     *
     * @return
     */
    @Override
    public List<Excel> readExcel(String filename) {
        File file = new File(FILE_PATH + filename);
        List<Excel> excelList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            for (Row row : sheet) {
                Excel excel = new Excel();

                excel.setNum(dataFormatter.formatCellValue(row.getCell(0)));
                excel.setName(dataFormatter.formatCellValue(row.getCell(1)));
                // registeredLessonsCell = rlc
                Cell rlc = row.getCell(2);
                if (rlc.getCellType() == CellType.NUMERIC) {
                    excel.setRegisteredLessons((int) rlc.getNumericCellValue());
                } else {
                    excel.setRegisteredLessons(0); // 숫자가 아닌 경우 0으로 설정하거나 다른 처리 방식 선택
                }

                excel.setReceivedLessons(dataFormatter.formatCellValue(row.getCell(3)));
                excel.setRegisteredDate(dataFormatter.formatCellValue(row.getCell(4)));

                excelList.add(excel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelList;
    }


    /**
     * 엑셀
     * 다운로드
     */
    @Override
    public List<EmpDTO> downloadExcel(HttpServletResponse response) throws IOException {
        List<EmpDTO> list = new ArrayList<>();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("게시판글들");
        int rowNo = 0;

        Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("사원 번호");
        headerRow.createCell(1).setCellValue("이름");
        headerRow.createCell(2).setCellValue("전화번호");
        headerRow.createCell(3).setCellValue("이메일");


/*      db 받는 곳
        for (EmpDTO empDTO : list) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(empDTO.getEmpNo());
            row.createCell(1).setCellValue(empDTO.getEmpNm());
            row.createCell(2).setCellValue(empDTO.getEmpPhn());
            row.createCell(3).setCellValue(empDTO.getEmpEml());
        }
*/

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=empList.xls");

        workbook.write(response.getOutputStream());
        workbook.close();
        return list;
    }


}