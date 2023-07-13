package com.example.test.api.excel.service.impl;

import com.example.test.api.excel.service.ExcelService;
import com.example.test.api.excel.vo.Excel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/static/excel/";
    private static final String FILE_DOWNLOAD = System.getProperty("user.dir") + "/src/main/resources/static/excel/";

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
    public int downloadExcel(String filename) {
        File file = new File(FILE_DOWNLOAD + filename + ".xlsx");

        Sheet sheet;
        Row row;
        Cell cell;

        try (FileOutputStream fos = new FileOutputStream(file);
             Workbook workbook = new XSSFWorkbook()) {
            int rowNo = 0; // 행의 갯수

            sheet = workbook.createSheet("워크시트1"); // 워크시트 이름 설정

            // 셀 병합
            // 첫행, 마지막행, 첫열, 마지막열 병합
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));

            // 타이틀 생성
            row = sheet.createRow(rowNo++); // 행 객체 추가
            cell = row.createCell(0); // 추가한 행에 셀 객체 추가
            cell.setCellValue("타이틀 입니다"); // 데이터 입력

            sheet.createRow(rowNo++);
            row = sheet.createRow(rowNo++);
            cell = row.createCell(0);
            cell.setCellValue("셀1");
            cell = row.createCell(1);
            cell.setCellValue("셀2");
            cell = row.createCell(2);
            cell.setCellValue("셀3");
            cell = row.createCell(3);
            cell.setCellValue("셀4");

            return 1; // 파일 작성이 성공 1
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0; // 예외나 실패 0
    }
}