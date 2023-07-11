package com.example.test.emp.service.impl;

import com.example.test.emp.dao.EmpDao;
import com.example.test.emp.dto.EmpDTO;
import com.example.test.emp.service.EmpService;
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
public class EmpServiceImpl implements EmpService {


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
     * 회원가입
     */
    @Override
    public int insertUser(EmpDTO empDTO) {
        // 회원가입 로직 구현
        return empDao.insertUser(empDTO);
    }

    @Override
    public FileVo signUploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }
        // 파일 업로드 처리
        FileVo fileVo = uploadFile(file);
        // 여기에서 파일 정보를 활용하여 추가적인 작업 수행 (예: 파일 경로 저장, 파일 정보 DB 등록)
        return fileVo;
    }

    /**
     * 사원 정보 검색
     */
    @Override
    public EmpRes getEmpListAndSearch(EmpReq req) {
        EmpRes res = new EmpRes();
        res.setPage(req.getPage());
        res.setListSize(req.getListSize());

        int count = this.selectBoardListCnt(req);
        res.setListCnt(count);

        if (count > 0) {
            res.setList(empDao.selectBoardList(req));
        }

        return res;
    }

    @Override
    public int selectBoardListCnt(EmpReq req) {
        return empDao.selectBoardListCnt(req);
    }

    /**
     * 사원목록
     * update
     */
    @Override
    public int empListUpdate(EmpDTO empDTO) {
        return empDao.empListUpdate(empDTO);
    }

/*    public int empListUpdate(EmpDTO empDTO) {
    if (StringUtils.mpty(empDTO.getEmpPwd())){
        return 0;
    }
    if (StringUtils.isEmpty(empDTO.getEmpNm())){
        return 0;
    }
    if (StringUtils.isEmpty(empDTO.getEmpPhn())){
        return 0;
    }
    return empDao.empListUpdate(empDTO);
}*/

    /**
     * 사원목록
     * 전체 가져오기
     */
    @Override
    public int getBoardListCnt() throws Exception {
        return empDao.getBoardListCnt();

    }

    /**
     * 파일
     * upload
     * isEmpty = 문자열 0인경우 true 리턴
     * IllegalArgumentException - 잘못된 파라미터가 넘어가는거 check
     */
    @Override
    public FileVo uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {   //파일 확인
            throw new IllegalArgumentException("파일이 없습니다.");
        }
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(FILE_UPLOAD_PATH + file.getOriginalFilename())) { //파일 이름,경로
            byte[] buffer = new byte[8192]; //바이트 크기 // buffer - 데이터 임시 저장, 전송
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {  // 데이터를 읽기 // -1를 반환하면 데이터 x
                outputStream.write(buffer, 0, bytesRead);  // bu 저장 데이터 -> out 출력
            }
        }
        FileVo fileVo = new FileVo();
        fileVo.setFileName(file.getOriginalFilename());
        fileVo.setFilePath(FILE_UPLOAD_PATH + file.getOriginalFilename());

        return fileVo;
    }


    /**
     * 파일
     * tostring
     */
//    @Override
//    public FileVo lessonFile(MultipartFile file) throws IOException {
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("파일이 없습니다.");
//        }
//        //시퀸스 = 생성해주는 오라클 객체
//        //ByteArrayOutputStream =  바이트 배열을 차례대로 읽기
//        String fileContent;
//        try (InputStream inputStream = file.getInputStream();
//             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) { // 지정된 byte 시퀀스 생성
//            byte[] buffer = new byte[8192]; //바이트 크기 // buffer - 데이터 임시 저장, 전송
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) { // 데이터를 읽기 // -1를 반환하면 데이터 x
//                outputStream.write(buffer, 0, bytesRead); // bu 저장 데이터 -> out 출력
//            }
//
//            // 파일 내용을 문자열로 변환하여 공백으로 구분
//            // 주어진 문자열에서 바이트를 얻습니다.
//            fileContent = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
//            fileContent = fileContent.replace("\n", " ").replace("\r", " ").replaceAll("\\s+", " ");
//        }
//
//        FileVo fileVo = new FileVo();
//        fileVo.setFileName(file.getOriginalFilename());
//        fileVo.setFilePath(FILE_LESSON_PATH + file.getOriginalFilename());
//        fileVo.setFileContent(fileContent);
//
//        return fileVo;
//    }

    /**
     * 파일
     * BufferedReader
     * isEmpty = 문자열 길이 0일때 true 리턴
     */

    @Override
    public List<Lesson> readFile(String fileName) {
        List<Lesson> lessonList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while (!(line = bufferedReader.readLine()).isEmpty()) {
                String[] tokens = line.split(" ");

                Lesson lesson = new Lesson();
                lesson.setName(tokens[0]);
                lesson.setRegisteredDate(tokens[2]);

                // "10회"에서 "회"를 제거하고 숫자만 추출하여 변환
                int registeredLessons = Integer.parseInt(tokens[4].replaceAll("[^\\d]", ""));
                lesson.setRegisteredLessons(registeredLessons);

                int receivedLessons = Integer.parseInt(tokens[6].replaceAll("[^\\d]", ""));
                lesson.setReceivedLessons(receivedLessons);

                // 남은 횟수 계산
                // 남은 횟수 = 등록 - 받은
                int remainingCount = registeredLessons - receivedLessons;
                lesson.setRemainingLessons(remainingCount);

                lessonList.add(lesson);

            }
        } catch (IOException e) {
            e.printStackTrace();
            // 오류 처리
        }
        return lessonList;
    }

    /**
     * 파일
     * 횟수 별
     * 카운팅
     */
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

    /**
     * 파일
     * 횟수 별
     * 등록된 사람들
     */
    @Override
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
    }

    /**
     * 엑셀
     * 업로드
     */
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


}
