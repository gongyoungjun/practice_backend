package com.example.test.api.vacation.dao;

import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.vacation.vo.VacationDTO;
import com.example.test.api.vacation.vo.VcCreate;
import com.example.test.api.vacation.vo.VcReq;
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
        return sqlSession.selectOne("mapper.empMapper.selectBoardListCnt", req);
    }

    /**
     * 휴가
     * 신청
     */
    public int createVacation(VcCreate vcCreate) {
        return sqlSession.insert("mapper.vcMapper.insertUser", vcCreate);
    }

    /**
     * 휴가
     * 목록
     */
    public VacationDTO getVacation(VcReq req) {
        return sqlSession.selectOne("mapper.vcMapper.getVacation", req);
    }
    /**
     * 휴가
     * 상세페이지
     */
    public VacationDTO getVcDetail(int vctnNo) {
        return sqlSession.selectOne("mapper.vcMapper.getVcDetail", vctnNo);
    }
    /**
     * 휴가
     * 승인,취소
     */
    public int updateVacationStatus(VacationDTO vacationDTO) {
        return sqlSession.update("mapper.vcMapper.updateVacationStatus", vacationDTO);
    }

}
