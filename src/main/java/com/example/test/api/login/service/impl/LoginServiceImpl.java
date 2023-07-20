package com.example.test.api.login.service.impl;

import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.login.dao.ServiceDao;
import com.example.test.api.login.service.LoginService;
import com.example.test.api.login.vo.LoginReq;
import com.example.test.api.login.vo.LoginRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final ServiceDao serviceDao;

    @Override
    public List<EmpDTO> selectLoginUser(LoginReq lReq) throws Exception {
        return serviceDao.selectBoardList(lReq);
    }
}