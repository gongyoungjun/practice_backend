package com.example.test.api.login.service;

import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.login.vo.LoginReq;

import java.util.List;

public interface LoginService {


    List<EmpDTO> selectLoginUser(LoginReq lReq) throws Exception;
}
