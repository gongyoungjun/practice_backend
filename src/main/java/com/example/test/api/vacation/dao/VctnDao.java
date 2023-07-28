package com.example.test.api.vacation.dao;

import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.vacation.vo.VacationDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VctnDao {
    private final SqlSessionTemplate sqlSession;

    /**
     * 휴가  조회
     */
    public List<VacationDTO> getVcEmpNo(EmpReq req) {
        return sqlSession.selectList("mapper.vcMapper.getVcEmpNo", req);
    }
    /**
     * 사원목록
     * 전체
     */
    public int selectBoardListCnt(EmpReq req) {
        return sqlSession.selectOne("mapper.empMapper.selectBoardListCnt");
    }

    /**
     * 휴가
     * 신청
     */
    public List<VacationDTO> createVacation(VacationDTO vacationDTO) {
        return sqlSession.selectList("mapper.vcMapper.getVcEmpNo", vacationDTO);
    }

}
