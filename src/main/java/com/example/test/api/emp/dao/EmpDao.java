package com.example.test.api.emp.dao;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.Pagination;
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
     * 사원 목록 조회
     *
     * @param req 사원 요청 정보
     * @return 사원 목록
     */
    public List<EmpDTO> selectBoardList(EmpReq req) {
        return sqlSession.selectList("mapper.empMapper.selectBoardList", req);
    }

    /**
     * 사원 목록 개수 조회
     *
     * @return 사원 목록 개수
     * @throws Exception 예외
     */
    public int getBoardListCnt() throws Exception {
        return sqlSession.selectOne("mapper.empMapper.getBoardListCnt");
    }

    /**
     * 사원 목록 조회
     *
     * @param res 사원 응답 정보
     * @return 사원 목록
     */
    public List<EmpDTO> empList(Pagination pagination) {
        return sqlSession.selectList("mapper.empMapper.empList", pagination);
    }

    /**
     * 사원목록
     * update
     * 수정예정
     */
    public int empListUpdate(EmpDTO empDTO) {
        return sqlSession.update("mapper.empMapper.empListUpdate", empDTO);
    }


    /**
     * 사원목록
     * 전체
     */
    public int selectBoardListCnt(EmpReq req) {
        return sqlSession.selectOne("mapper.empMapper.selectBoardListCnt");
    }


    public List<EmpDTO> getEmpList() {
        return sqlSession.selectList("mapper.empMapper.selectExcelList");
    }
}

