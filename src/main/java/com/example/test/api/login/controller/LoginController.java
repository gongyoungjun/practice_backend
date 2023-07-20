package com.example.test.api.login.controller;

import com.example.test.api.config.Code;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.login.service.LoginService;
import com.example.test.api.login.vo.LoginReq;
import com.example.test.api.login.vo.LoginRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "사용자 관련")
public class LoginController {

    private final LoginService loginService;


    @Operation(summary = "로그인 Action", description = "로그인 처리 API")
    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq lReq) {
        LoginRes loginRes = new LoginRes();
        String code = Code.SUCCESS;
        List<EmpDTO> data = null;
        try {
            data = loginService.selectLoginUser(lReq);
        } catch (Exception e) {
            code = Code.FAIL;
        }
        loginRes.setData(data);
        loginRes.setCode(code);
        return ResponseEntity.ok(loginRes);
    }
}
