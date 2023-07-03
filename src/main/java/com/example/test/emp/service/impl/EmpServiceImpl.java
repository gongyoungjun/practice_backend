package com.example.test.emp.service.impl;

import com.example.test.emp.dto.EmpDTO;
import com.example.test.emp.dao.EmpDao;
import com.example.test.emp.service.EmpService;
import com.example.test.emp.vo.EmpReq;
import com.example.test.emp.vo.EmpRes;
import com.example.test.emp.vo.FileVo;
import com.example.test.emp.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class EmpServiceImpl implements EmpService {
    private static final String FILE_UPLOAD_PATH = "C:\\Users\\urbancode\\Desktop\\practice\\test-master\\src\\main\\resources\\static.image\\";
    private final EmpDao empDao;

    @Autowired
    public EmpServiceImpl(EmpDao empDao) {
        this.empDao = empDao;
    }

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
     * 사원 정보 목록 및 프로필 검색
     */
    @Override
    public Pagination getEmpListAndSearch(String empNm, String empPhn, Pagination pagination) throws Exception {
        pagination.setListCnt(empDao.getBoardListCnt());
        List<EmpDTO> empList;

        if (empNm != null || empPhn != null) {
            // 사원 이름 또는 전화번호가 존재하는 경우 사원 프로필을 검색
            EmpReq req = new EmpReq();
            req.setEmpNm(empNm);
            req.setEmpPhn(empPhn);
            EmpRes res = selectBoardList(req);
            empList = res.getList();
        } else {
            // 사원 이름과 전화번호가 모두 존재하지 않는 경우 전체 사원 목록 조회
            empList = empDao.empList(pagination);
        }

        if (empList == null) {
            empList = new ArrayList<>(); // 빈 리스트로 초기화
        }

        pagination.setEmpList(empList);

        return pagination;
    }

    /**
     * 사원목록
     * get
     */
    @Override
    public List<EmpDTO> empList(Pagination pagination) {
        return empDao.empList(pagination);
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
     * 사원목록
     * search
     */
    @Override
    public EmpRes selectBoardList(EmpReq req) {
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
        if (file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }
        try {
            // 파일을 저장하거나 처리하는 로직을 구현
            // 예를 들어, 파일을 디스크에 저장하거나 데이터베이스에 저장
            // 여기에서는 파일의 이름과 경로를 생성하여 FileVo 객체에 저장하여 반환
            String fileName = file.getOriginalFilename();
            String filePath = FILE_UPLOAD_PATH + fileName;
            File saveFile = new File(filePath);
            file.transferTo(saveFile); // 경로 지정해서 저장.

            FileVo fileVo = new FileVo();
            fileVo.setFileName(fileName);
            fileVo.setFilePath(filePath);

            return fileVo;
        } catch (IOException e) {
            // IOException
            // 예외처리
            // throw - 던지다
            throw new IOException("파일 업로드 중 오류가 발생했습니다.", e);
        }
    }

}

