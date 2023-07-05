package com.example.test.emp.service;

import com.example.test.emp.dto.EmpDTO;
import com.example.test.emp.vo.EmpReq;
import com.example.test.emp.vo.EmpRes;
import com.example.test.emp.vo.FileVo;
import com.example.test.emp.vo.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmpService {

    /**
     * 회원가입
     */
    int insertUser(EmpDTO empDTO);

    /**
     * 사원목록
     * 검색 및 목록
     */
    EmpReq getEmpListAndSearch(EmpReq req) throws Exception;

    /**
     * 사원목록
     * get
     */
    List<EmpDTO> empList(Pagination pagination);

    /**
     * 사원목록
     * search
     */


    int getBoardListCnt() throws Exception;

    EmpRes selectBoardList(EmpReq req);
    int selectBoardListCnt(EmpReq req);

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
}
