package com.example.test.api.login.dao;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.login.vo.LoginReq;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ServiceDao {

    private final SqlSessionTemplate sqlSession;

    /**
     * 로그인
     */
    public List<EmpDTO> selectBoardList(LoginReq lReq) {
        return sqlSession.selectList("mapper.empMapper.selectLogin", lReq);
    }
}
