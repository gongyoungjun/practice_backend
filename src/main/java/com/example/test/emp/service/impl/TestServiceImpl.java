package com.example.test.emp.service.impl;

import com.example.test.emp.dao.EmpDao;
import com.example.test.emp.dto.EmpDTO;
import com.example.test.emp.service.TestService;
import com.example.test.emp.vo.EmpReq;
import com.example.test.emp.vo.EmpRes;
import com.example.test.emp.vo.FileVo;
import com.example.test.emp.vo.Lesson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {


    /**
     * system.getProperty : 시스템의 정보를 가져 올 때
     * user.dir = 현재 디렉토리(폴더)
     * user.home = 사용자 홈 디렉토리
     * user.name = 사용자 계정
     */
    private static final String FILE_UPLOAD_PATH = System.getProperty("user.dir") + "/src/main/resources/static/image/";

    private final String filePath = System.getProperty("user.dir") + "/src/main/resources/static/file/lesson.txt";
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/static/excel/연습.xlsx";
    private final EmpDao empDao;




    /**
     * 파일
     * 횟수 별
     * 카운팅
     */
/*
    @Override
    public List<Lesson> registered(String fileName) {
        List<Lesson> lessonList = readFile(fileName); // 레슨 정보 가져옴

        Map<Integer, Integer> countMap = new HashMap<>();

        //list에서 lesson을 하나 씩 가져옴
        for (Lesson lesson : lessonList) {
            int registeredLessons = lesson.getRegisteredLessons();
            int count = countMap.getOrDefault(registeredLessons, 0);
            countMap.put(registeredLessons, count + 1);
        }

        List<Lesson> countList = new ArrayList<>();
        //map.entry = Map 형태의 인터페이스
        //countMap.entrySet()에서 반환
        //Map.Entry 객체들을 차례대로 가져와서 할당
        //키와 값 가져옴
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            int registeredLessons = entry.getKey();
            int count = entry.getValue();

            if (registeredLessons % 10 == 0) {
                Lesson countLesson = new Lesson();
                countLesson.setName(registeredLessons + "회");
                countLesson.setRegisteredLessons(count);
                countList.add(countLesson);
            }
        }
        // 10회 단위로 정렬
        return countList;
    }
*/



    /**
     * 파일
     * 횟수 별
     * 등록된 사람들
     */
/*    @Override
    public Map<String, List<String>> getRegisteredByCount(String fileName) {
        List<Lesson> lessonList = readFile(fileName);

        Map<Integer, List<String>> countMap = new HashMap<>();

        for (Lesson lesson : lessonList) {
            int registeredLessons = lesson.getRegisteredLessons();
            String name = lesson.getName();

            int countKey = registeredLessons / 10 * 10; // 10회 단위로

            // countKey가 없을 때, 새로운 ArrayList를 생성하여 맵에 추가
            //putIfAbsent = key 값이 존재 - value 반환
            //              key 값이 존재x - value map에 저장 null 반환
            countMap.putIfAbsent(countKey, new ArrayList<>());

            // 해당 그룹에 이름 추가
            countMap.get(countKey).add(name);
        }

        // 결과 맵을 정렬 - TreeMap으로 변환
        // treemap = haspmap과 비슷, 크기 지정X
        Map<String, List<String>> result = new TreeMap<>();

        for (Map.Entry<Integer, List<String>> entry : countMap.entrySet()) {
            int countKey = entry.getKey();
            List<String> names = entry.getValue();

            result.put(countKey + "회", names);
        }

        return result;
    }*/

    /**
     * 엑셀
     * 업로드
     */
/*
    @Override
    public void readExcelFile() {
        File file = new File(FILE_PATH);

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {

            // 엑셀 파일에서 첫 번째 시트 가져오기
            Sheet sheet = workbook.getSheetAt(0);

            // 각 행 반복
            for (Row row : sheet) {
                // 각 셀 반복
                for (Cell cell : row) {
                    // 셀 내용 읽기
                    String cellValue = cell.getStringCellValue();
                    System.out.print(cellValue + "\t");
                }
                System.out.println(); // 다음 행으로 넘어갈 때 개행
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/


}
