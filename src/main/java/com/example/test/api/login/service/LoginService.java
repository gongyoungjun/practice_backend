package com.example.test.api.login.service;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.login.vo.LoginReq;

public interface LoginService {

    EmpDTO selectLoginUser(LoginReq lReq) throws Exception;
}
