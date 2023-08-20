package com.example.test.api.emp.dao;

import com.example.test.api.emp.dto.EmpCommuteDTO;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.vacation.vo.VacationDTO;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.login.vo.LoginReq;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmpDao {

    private final SqlSessionTemplate sqlSession;


    /**
     * 회원가입 페이지 > 회원가입 > 데이터 넣기
     */
    public int insertUser(EmpDTO empDTO) {
        return sqlSession.insert("mapper.empMapper.insertUser", empDTO);
    }

    /**
     * 카카오 회원가입 페이지 > 회원가입 > 데이터 넣기
     */
    public int insertKakaoUser(EmpDTO empDTO) {
        return sqlSession.insert("mapper.empMapper.insertKakaoUser", empDTO);
    }

    /**
     * 사원 목록 조회
     */
    public List<EmpDTO> selectBoardList(EmpReq req) {
        return sqlSession.selectList("mapper.empMapper.selectBoardList", req);
    }

    /**
     * 사원목록
     * 전체
     */
    public int selectBoardListCnt(EmpReq req) {
        return sqlSession.selectOne("mapper.empMapper.selectBoardListCnt");
    }

    /**
     * 사원 목록 개수 조회
     */
    public int getBoardListCnt() throws Exception {
        return sqlSession.selectOne("mapper.empMapper.getBoardListCnt");
    }

    /**
     * 사원 상세 조회
     */
    public EmpDTO getEmpByEmpNo(int empNo) {
        return sqlSession.selectOne("mapper.empMapper.getEmpByEmpNo", empNo);
    }

    /**
     * 사원목록
     * update
     */
    public int empListUpdate(EmpDTO empDTO) {
        return sqlSession.update("mapper.empMapper.empListUpdate", empDTO);
    }

    /**
     * 엑셀
     * 다운로드
     */
    public List<EmpDTO> getEmpList() {
        return sqlSession.selectList("mapper.empMapper.selectExcelList");
    }

    /**
     * 로그인
     */
    public List<EmpDTO> selectBoardList(LoginReq lReq) {
        return sqlSession.selectList("mapper.empMapper.selectLogin", lReq);
    }

    /**
     * 출퇴근
     */
    public int insertCommute(EmpCommuteDTO empCommuteDTO) {
        return sqlSession.insert("mapper.empMapper.insertCommute", empCommuteDTO);
    }

    /**
     * 이메일 가입여부
     */
    public EmpDTO getEmpByEmpEml(String empEml) {
        return sqlSession.selectOne("mapper.empMapper.getEmpByEmpEml", empEml);
    }

    /**
     * id 토큰 있는지
     */
    public String getIdToken(String idToken) {
        return sqlSession.selectOne("mapper.empMapper.getIdToken", idToken);
    }

    /**
     * sns키 있는지
     */
    public EmpDTO selectSnsKey(String  snsKey) {
        return sqlSession.selectOne("mapper.empMapper.selectSnsKey", snsKey);
    }


    /**
     * sns키로 update
     */
    public EmpDTO UpdateSnsKey(String snsKey) {
        return sqlSession.selectOne("mapper.empMapper.selectSnsKey", snsKey);
    }


    /**
     * sns키로 update
     */
    public int kakaoEmpUpdate(EmpDTO empDTO) {
        return sqlSession.update("mapper.empMapper.kakaoUpdate", empDTO);
    }
}

