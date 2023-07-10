package com.example.test.emp.service.impl;

import com.example.test.emp.dto.EmpDTO;
import com.example.test.emp.dao.EmpDao;
import com.example.test.emp.service.EmpService;
import com.example.test.emp.vo.EmpReq;
import com.example.test.emp.vo.EmpRes;
import com.example.test.emp.vo.FileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


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

/*    @Override
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

            file.transferTo(saveFile);

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
    } */


}
