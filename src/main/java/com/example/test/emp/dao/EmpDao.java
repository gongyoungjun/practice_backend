package com.example.test.emp.dao;

import com.example.test.emp.dto.EmpDTO;
import com.example.test.emp.vo.EmpReq;
import com.example.test.emp.vo.FileVo;
import com.example.test.emp.vo.Pagination;
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
     * 사원목록
     * get
     */
    public List<EmpDTO> empList(Pagination pagination) {
        return sqlSession.selectList("mapper.empMapper.empList", pagination);
    }

    /**
     * 사원목록
     * update
     */
    public int empListUpdate(EmpDTO empDTO) {
        return sqlSession.update("mapper.empMapper.empListUpdate", empDTO);
    }

    /**
     * 사원목록
     * search
     */
    public List<EmpDTO> selectBoardList(EmpReq req) {
        return sqlSession.selectList("mapper.empMapper.selectBoardList", req);
    }

    /**
     * 사원목록
     * 페이징 데이터 가져오기
     */
    public int getBoardListCnt() throws Exception {
        return sqlSession.selectOne("mapper.empMapper.getBoardListCnt");

    }

    /**
     * 사원목록
     * 전체
     */
    public int selectBoardListCnt(EmpReq req) {
        return sqlSession.selectOne("mapper.empMapper.selectBoardListCnt");
    }

    /**
     * 파일
     * upload
     */

}

