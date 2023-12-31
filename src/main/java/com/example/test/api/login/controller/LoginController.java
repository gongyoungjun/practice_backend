package com.example.test.api.login.controller;

import com.example.test.api.config.Code;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.jwt.JwtUtil;
import com.example.test.api.login.service.LoginService;
import com.example.test.api.login.vo.LoginReq;
import com.example.test.api.login.vo.LoginRes;
import com.example.test.api.util.PasswordUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "사용자 관련")
public class LoginController {

    private final LoginService loginService;
    private final PasswordUtil passwordUtil;
//    private final String KAKAO_USERINFO_ENDPOINT = "https://kapi.kakao.com/v2/user/me";


    /**
     * 로그인
     */
    //백엔드에서 로그인 성공여부 -> code / 조회 -> data
    //프론트에서 code -> 성공,실패 구분 / data 변수 받아서 성공
    @Operation(summary = "로그인 Action", description = "로그인 처리 API")
    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq lReq) {
        LoginRes loginRes = new LoginRes();
        String code = Code.FAIL;
        System.out.println("Initial value of code: " + code);
        try {
            String pwd = passwordUtil.sha256Encrypt(lReq.getEmpPwd());
            lReq.setEmpPwd(pwd);

            EmpDTO data = loginService.selectLoginUser(lReq);
            // 저장된 비밀번호 해시값을 계산

            if (data != null) {
                // 로그인 성공
                code = Code.SUCCESS;

                // JWT 토큰 생성 (jjwt 사용)
                String jwtToken = JwtUtil.generateToken(data.getEmpNo());
                loginRes.setToken(jwtToken);  // Token을 응답에 추가
                loginRes.setData(data);  // EmpDTO를 리스트에 넣어서 설정
            } else {
                code = Code.FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = Code.FAIL; // 기본적으로 실패로 설정
        }

        loginRes.setCode(code);
        System.out.println("Final value of code: " + code);
        return ResponseEntity.ok(loginRes);
    }
}
