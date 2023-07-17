package com.example.test.api.excel.service.impl;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.excel.service.ExcelService;
import com.example.test.api.excel.vo.Excel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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
     * byte[] = 바이트 배열로 변환하여 반환(inputstream 생각)
     */
    @Override
    public byte[] downloadExcel(List<EmpDTO> list) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");
        int rowNo = 0; //지역변수 0
        // 헤더 생성
        Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("사원 번호");
        headerRow.createCell(1).setCellValue("이름");
        headerRow.createCell(2).setCellValue("전화번호");
        headerRow.createCell(3).setCellValue("이메일");

        // 데이터 생성
        for (EmpDTO empDTO : list) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(empDTO.getEmpNo());
            row.createCell(1).setCellValue(empDTO.getEmpNm());
            row.createCell(2).setCellValue(empDTO.getEmpPhn());
            row.createCell(3).setCellValue(empDTO.getEmpEml());
        }
        //바이트 배열로 변환
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream); // 사용하기 위해 호출
            return outputStream.toByteArray(); //저장된 모든 바이트 데이터를 반환
        } catch (IOException e) {
            // 예외 처리
        }
        //빈 데이터 반환 = 데이터 x
        return new byte[0];
    }

}