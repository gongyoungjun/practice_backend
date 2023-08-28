package com.example.test.api.login.service.impl;

import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.login.service.LoginService;
import com.example.test.api.login.vo.LoginReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final EmpDao empDao;

    @Override
    public EmpDTO selectLoginUser(LoginReq lReq){
        return empDao.selectBoardList(lReq);
    }

    @Override
    public EmpDTO findUser(LoginReq lReq) {
        return empDao.findUser(lReq);
    }
}