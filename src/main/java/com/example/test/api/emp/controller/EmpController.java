package com.example.test.api.emp.controller;

import com.example.test.api.config.Code;
import com.example.test.api.emp.dto.EmpCommuteDTO;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.service.EmpService;
import com.example.test.api.emp.vo.*;
import com.example.test.api.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "사용자 관련")
public class EmpController {

    private final EmpService empService;

    /**
     * 회원가입
     * PostMapping - consuemes
     */
    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<String> insertUser(@RequestPart("file") MultipartFile file, @RequestBody EmpDTO empDTO) {
        String code = Code.FAIL;
        try {
            // 회원가입 로직 구현
            log.debug("회원가입 {}", empDTO);
            // 파일 업로드 처리
            if (!file.isEmpty()) {
                empService.signUploadFile(file);
            }
            int res = empService.insertUser(empDTO);
            if (res > 0) {
                code = Code.SUCCESS;
            }

        } catch (IOException e) {
            code = Code.FAIL;
        }
        return ResponseEntity.ok(code);
    }

    /**
     * 사원 정보 검색
     */
    @Operation(summary = "사원 정보 검색", description = "사원 정보 검색 API")
    @PostMapping("/emp/list")
    public ResponseEntity<EmpRes> getEmpListAndSearch(@RequestBody EmpReq req) throws Exception {
        EmpRes resultEmpRes = new EmpRes();
        String code = Code.SUCCESS;
        try {
            resultEmpRes = empService.getEmpListAndSearch(req);
        } catch (Exception e) {
            code = Code.FAIL;
        }
        resultEmpRes.setCode(code);
        return ResponseEntity.ok(resultEmpRes);
    }

    /**
     * 사원
     * 상세 정보
     */
    @Operation(summary = "사원 상세 정보", description = "사원 상세 정보 API")
    @PostMapping("/emp/detail")
    public ResponseEntity<EmpDetailRes> setEmpList(@RequestBody EmpReq req) {
        EmpDetailRes res = new EmpDetailRes();
        String code = Code.SUCCESS;

        try {
            if (req.getEmpNo() > 0) {
                res.setData(empService.selectEmpByEmpNo(req.getEmpNo()));
            }
        } catch (Exception e) {
            code = Code.FAIL;
        }

        res.setCode(code);
        return ResponseEntity.ok(res);
    }

    /**
     * 사원 정보 수정 API
     */
    @Operation(summary = "사원 정보 수정", description = "사원 정보 수정 api")
    @PutMapping("/emp/update")
    public ResponseEntity<EmpRes> setEmpListUpdate(@RequestBody EmpDTO empDTO) {
        EmpRes res = new EmpRes();
        String code = Code.SUCCESS;
        try {
            int result = empService.empListUpdate(empDTO);
            if (result > 0) {
                code = Code.SUCCESS;
            }
            res.setData(result);
        } catch (Exception e) {
            code = Code.FAIL;
            e.printStackTrace();
        }

        res.setCode(code);
        return ResponseEntity.ok(res);
    }

    /**
     * 파일
     * upload
     * isEmpty = 문자열 0인경우 true 리턴
     * IOException : throw(던지다)된 예외에 대한 기본 클래스
     * RequestParam - 요청 매개변수
     */
    @Operation(summary = "파일 업로드", description = "파일 업로드")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            empService.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Code.FAIL);
        }
        return ResponseEntity.ok(Code.SUCCESS);
    }

    /**
     * 파일
     * losson
     * @PathVariable = 템플릿 변수 처리 {}
     */
    @Operation(summary = "Lesson", description = "Lesson")
    @GetMapping("/read-file/{fileName}")
    public ResponseEntity<LessonRes> readFile(@PathVariable String fileName) {
        String code = Code.FAIL;

        LessonRes lessonRes = null;
        try {
            lessonRes = empService.readFile(fileName);
            if (lessonRes != null && !lessonRes.getLessonList().isEmpty()) {
                code = Code.SUCCESS;
            }

        } catch (LessonException e) {
            e.printStackTrace();
            code = Code.SUCCESS;
        }
        lessonRes.setCode(code);
        return ResponseEntity.ok(lessonRes);
    }

    /**
     * 출퇴근
     */
    @Operation(summary = "출퇴근", description = "출퇴근")
    @PostMapping("/emp/commute")
    public ResponseEntity<EmpRes> insertCommute(@RequestHeader("Authorization") String jwtToken, @RequestBody EmpCommuteDTO empCommuteDTO) {
        EmpRes res = new EmpRes();
        String code = Code.FAIL;

        try {
            int empNo = JwtUtil.extractEmpNo(jwtToken);
            empCommuteDTO.setEmpNo(empNo);

            int result = empService.insertCommute(empCommuteDTO);
            if (result > 0) {
                code = Code.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.setCode(code);
        return ResponseEntity.ok(res);
    }

    /**
     * 출퇴근
     * list
     */
    @Operation(summary = "사원 정보 검색", description = "사원 정보 검색 API")
    @PostMapping("/emp/commuteList")
    public ResponseEntity<EmpRes> selectCommuteList(@RequestBody EmpReq req) {
        EmpRes res = new EmpRes();
        String code = Code.SUCCESS;
        try {
            res = empService.selectCommuteList(req);
        } catch (Exception e) {
            code = Code.FAIL;
        }
        res.setCode(code);
        return ResponseEntity.ok(res);
    }

    /**
     * 사원 이메일
     * 가입 확인
     */
    @Operation(summary = "사원 이메일 확인", description = "사원 이메일 존재 여부 확인 API")
    @PostMapping("/emp/emailCheck")
    public ResponseEntity<EmpDetailRes> checkEmpEmail(@RequestBody EmpReq req) {
        EmpDetailRes res = new EmpDetailRes();
        String code = Code.SUCCESS;

        try {
            res.setData(empService.getEmpByEmpEml(req.getEmpEml()));
        } catch (Exception e) {
            code = Code.FAIL;
        }
        res.setCode(code);
        return ResponseEntity.ok(res);
    }

}

