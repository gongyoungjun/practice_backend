package com.example.test.api.kakao.controller;

import com.example.test.api.config.Code;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.jwt.JwtUtil;
import com.example.test.api.kakao.service.KakaoService;
import com.example.test.api.kakao.vo.KakaoFriend;
import com.example.test.api.kakao.vo.KakaoReq;
import com.example.test.api.kakao.vo.KakaoUserInfo;
import com.example.test.api.login.vo.LoginReq;
import com.example.test.api.login.vo.LoginRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.test.api.jwt.JwtUtil.generateToken;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    /**
     * 카카오
     * 인가코드
     */
    @Operation(summary = "카카오 토큰 가져오기", description = "카카오 인가 코드를 통해 액세스 토큰을 가져오는 API")
    @PostMapping("/token")
    public ResponseEntity<String> getKakaoToken(@RequestParam("code") String code) {
        String token = kakaoService.getKaKaoCode(code);
        return ResponseEntity.ok(token);
    }

    /**
     * 카카오
     * 회원가입
     */
    @Operation(summary = "카카오 회원가입", description = "카카오 회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<EmpRes> insertKakaoUser(@RequestBody EmpDTO empDTO) {
        EmpRes res = new EmpRes();
        String code = Code.SUCCESS;
        try {
            int result = kakaoService.insertKakaoUser(empDTO);
            if (result > 0) {
                code = Code.SUCCESS;
            }

        } catch (Exception e) {
            code = Code.FAIL;
        }

        res.setCode(code);
        return ResponseEntity.ok(res);
    }


    /**
     * snsKey
     * 가입 확인
     */
    @Operation(summary = "사원 이메일 확인", description = "사원 이메일 존재 여부 확인 API")
    @PostMapping("/snsKey")
    public ResponseEntity<EmpRes> checkEmpEmail(@RequestBody EmpReq req) {
        EmpRes res = new EmpRes();
        String code = Code.SUCCESS;
        List<EmpDTO> list = null;

        try {
            if (req.getSnsKey() != null && !req.getSnsKey().isEmpty()) {
                EmpDTO empDTO = kakaoService.selectSnsKey(req.getSnsKey());
                if (empDTO != null) {
                    list = new ArrayList<>();
                    list.add(empDTO);
                }
            } else {
                code = Code.FAIL;
            }
        } catch (Exception e) {
            code = Code.FAIL;
        }

        res.setList(list);
        res.setCode(code);
        return ResponseEntity.ok(res);
    }
//2963323795
}