package com.example.test.api.emp.service;

import com.example.test.api.emp.dto.EmpCommuteDTO;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.emp.vo.FileVo;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.LessonRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmpService {

    /**
     * 회원가입
     */
    int insertUser(EmpDTO empDTO);

    /**
     * 카카오 회원가입
     */
    int insertKakaoUser(EmpDTO empDTO);

    /**
     * 사원목록
     * 검색 및 목록
     */
    EmpRes getEmpListAndSearch(EmpReq req) throws Exception;

    /**
     * 사원목록
     * search
    int getBoardListCnt() throws Exception;

    int selectBoardListCnt(EmpReq req);

    /**
     * 사원
     * 상세 정보
     */
    EmpDTO getEmpByEmpNo(int empNo);


    /**
     * 사원목록
     * update
     */
    int empListUpdate(EmpDTO empDTO);

    /**
     * 파일
     * upload
     */
    FileVo uploadFile(MultipartFile file) throws IOException;

    /**
     * 회원가입
     * 이미지
     * upload
     */
    FileVo signUploadFile(MultipartFile file) throws IOException;

    /**
     * 파일
     * lesson
     */
    LessonRes readFile(String fileName);


    /**
     * 출퇴근
     */
    int insertCommute(EmpCommuteDTO empCommuteDTO);


    /**
     * 출퇴근
     */
    EmpDTO getEmpByEmpEml(String empEml);

}
