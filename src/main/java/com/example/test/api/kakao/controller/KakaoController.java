package com.example.test.api.kakao.controller;

import com.example.test.api.config.Code;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.jwt.JwtUtil;
import com.example.test.api.kakao.service.KakaoService;
import com.example.test.api.util.PasswordUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KakaoController {

    private final KakaoService kakaoService;
    private final PasswordUtil passwordUtil;

    /**
     * 카카오
     * 인가코드
     */
    @Operation(summary = "카카오 토큰 가져오기", description = "카카오 인가 코드를 통해 액세스 토큰을 가져오는 API")
    @PostMapping("/kakao/token")
    public ResponseEntity<String> getKakaoToken(@RequestParam("code") String code) {
        String token = kakaoService.getKaKaoCode(code);
        return ResponseEntity.ok(token);
    }

    /**
     * 카카오
     * 회원가입
     */
    @Operation(summary = "카카오 회원가입", description = "카카오 회원가입 API")
    @PostMapping("/kakao/signup")
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
     * 사원 정보 업데이트 API
     */
    @Operation(summary = "사원 정보 업데이트", description = "사원 정보 업데이트 api")
    @PutMapping("/kakao/update")
    public ResponseEntity<EmpRes> kakaoEmpUpdate(@Valid @RequestBody EmpDTO empDTO) {
        EmpRes res = new EmpRes();
        String code = Code.SUCCESS;
        // 비밀번호를 SHA-256로 해싱
        String hashedPassword = passwordUtil.sha256Encrypt(empDTO.getEmpPwd());
        empDTO.setEmpPwd(hashedPassword);

        try {
            int result = kakaoService.kakaoEmpUpdate(empDTO);
            if (result > 0) {
                // 사원 정보 업데이트 성공 시, JWT 토큰 생성
                String jwtToken = JwtUtil.generateToken(empDTO.getEmpNo());

                // 응답 헤더에 토큰 설정
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", jwtToken);

                res.setToken(jwtToken);
                res.setCode(code);

                return new ResponseEntity<>(res, headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            code = Code.FAIL;
        }

        res.setCode(code);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    /**
     * snsKey
     * 가입 확인
     */
    @Operation(summary = "snsKey 확인", description = "snsKey 존재 여부 확인 API")
    @PostMapping("/kakao/snsKey")
    public ResponseEntity<EmpRes> checkSnsKey(@RequestBody EmpReq req) {
        EmpRes res = new EmpRes();
        String code = Code.SUCCESS;
        List<EmpDTO> list = null;

        try {
            // snsKey와 empNm 모두 확인
            if (req.getSnsKey() != null && !req.getSnsKey().isEmpty()
                //  && req.getEmpPhn() != null && !req.getEmpPhn().isEmpty()
            ) {

                EmpDTO empDTO = kakaoService.selectSnsKey(req);
                if (empDTO != null) {
                    list = new ArrayList<>();
                    list.add(empDTO);
                    // snsKey가 유효하면 JWT 토큰을 생성
                    String jwtToken = JwtUtil.generateToken(empDTO.getEmpNo());
                    // 응답 헤더에 토큰 설정
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", jwtToken);

                    res.setToken(jwtToken);  // Token을 응답에 추가
                    res.setList(list);
                    res.setCode(code);

                    return new ResponseEntity<>(res, headers, HttpStatus.OK);
                } else {
                    code = Code.FAIL;
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

    /**
     * 사원
     * sns키로
     * 가입정보 불러오기
     */
    @Operation(summary = "사원 상세 정보", description = "사원 상세 정보 API")
    @PostMapping("/kakao/detail")
    public ResponseEntity<EmpRes> UpdateSnsKey(@RequestBody EmpReq req) {
        System.out.println(req);  // 요청 본문 출력
        EmpRes res = new EmpRes();
        String code = Code.SUCCESS;
        List<EmpDTO> list = null;
        String snsKey = req.getSnsKey();

        try {
            if (snsKey != null && !snsKey.trim().isEmpty()) {
                EmpDTO empDTO = kakaoService.UpdateSnsKey(req.getSnsKey());
                if (empDTO != null) {
                    list = new ArrayList<>();
                    list.add(empDTO);
                }
            }
        } catch (Exception e) {
            code = Code.FAIL;

        }

        res.setList(list);
        res.setCode(code);
        return ResponseEntity.ok(res);
    }

}