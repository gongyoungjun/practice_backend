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

    /**
     * 로그인
     * @param lReq
     * @return
     */
    //백엔드에서 로그인 성공여부 -> code / 조회 -> data
    //프론트에서 code -> 성공,실패 구분 / data 변수 받아서 성공
    @Operation(summary = "로그인 Action", description = "로그인 처리 API")
    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq lReq) {
        LoginRes loginRes = new LoginRes();
        String code = Code.FAIL; // 기본적으로 실패로 설정

        try {
            List<EmpDTO> data = loginService.selectLoginUser(lReq);
            if (data != null && !data.isEmpty()) {
                // 사용자 정보가 존재하면 로그인 성공으로 처리
                code = Code.SUCCESS;
            }
            loginRes.setData(data);
            loginRes.setCode(code);
            return ResponseEntity.ok(loginRes);
        } catch (Exception e) {
            // 예외 발생 시 실패로 처리
            loginRes.setCode(code);

        }return ResponseEntity.ok(loginRes);
    }
/*    @Operation(summary = "로그인 Action", description = "로그인 처리 API")
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
    }*/
}
