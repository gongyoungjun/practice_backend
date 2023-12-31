package com.example.test.api.emp.service.impl;

import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.emp.dto.EmpCommuteDTO;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.service.EmpService;
import com.example.test.api.emp.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


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

    @Value("${emp.file.upload}")
    private String FILE_UPLOAD_PATH;

    @Value("${emp.filePath}")
    private String filePath;


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
        return fileVo;
    }

    /**
     * 사원 정보 검색
     */
    @Override
    public EmpRes getEmpListAndSearch(EmpReq req) {

        List<EmpDTO> empList = null;

        EmpRes res = new EmpRes();
        res.setPage(req.getPage());
        res.setListSize(req.getListSize());

        int totalCount = empDao.selectBoardListCnt(req); // 변경: 총 데이터 개수를 따로 조회

        if (totalCount > 0) {
            empList = empDao.selectBoardList(req);
        }
        res.setTotal(totalCount);
        res.setList(empList);

        return res;
    }

    /**
     * 사원목록
     * update
     */
    @Override
    public int empListUpdate(EmpDTO empDTO) {
        return empDao.empListUpdate(empDTO);


    }

    /**
     * 사원
     * 상세 목록
     */
    public EmpDTO selectEmpByEmpNo(int empNo) {
        return empDao.selectEmpByEmpNo(empNo);
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
     * BufferedReader
     * isEmpty = 문자열 길이 0일때 true 리턴
     */

    @Override
    public LessonRes readFile(String fileName) {
        List<Lesson> lessonList = new ArrayList<>();
        LessonRes lessonRes;
        int count10 = 0;
        int count20 = 0;
        // bufferedReader 가 close() 메소드가 있다.
        // 안에 사용하면 별도로 close()메소드를 호출 해야함.
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + fileName))) {
            String line;
            Lesson lesson;

            while (!(line = bufferedReader.readLine()).isEmpty()) {
                String[] tokens = line.split(" ");

                lesson = new Lesson();
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

                // 10회/20회 구분
                if (registeredLessons == 10) {
                    count10++;
                } else if (registeredLessons == 20) {
                    count20++;
                }

                lessonList.add(lesson);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // 오류 처리
            throw new LessonException("오류 발생");
        }

        lessonRes = new LessonRes(lessonList, count10, count20);
        return lessonRes;
    }

    /**
     * 출퇴근
     */
    @Override
    public int insertCommute(EmpCommuteDTO empCommuteDTO) {
        return empDao.insertCommute(empCommuteDTO);
    }

    /**
     * 출퇴근
     * list
     */
    @Override
    public EmpRes selectCommuteList(EmpReq req) {
        List<EmpCommuteDTO> empCommuteDTO = null;

        EmpRes res = new EmpRes();
        res.setPage(req.getPage());
        res.setListSize(req.getListSize());

        int totalCount = empDao.selectBoardListCnt(req);

        if (totalCount > 0) {
            empCommuteDTO = empDao.selectCommuteList(req);
        }
        res.setTotal(totalCount);
        res.setEmpCommuteDTO(empCommuteDTO);

        return res;
    }

    /**
     * 이메일 가입여부
     */
    public EmpDTO getEmpByEmpEml(String empEml) {
        return empDao.getEmpByEmpEml(empEml);
    }


}
