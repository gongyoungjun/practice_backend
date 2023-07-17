package com.example.test.api.excel.dao;

import com.example.test.api.emp.dto.EmpDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class ExcelDao {

    private final SqlSessionTemplate sqlSession;

    public List<EmpDTO> selectExcelList(HttpServletResponse response) {
        return sqlSession.selectList("mapper.empMapper.selectBoardList", response);
    }



}
