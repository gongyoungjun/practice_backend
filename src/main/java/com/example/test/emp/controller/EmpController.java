package com.example.test.emp.controller;

import com.example.test.emp.dto.EmpDTO;
import com.example.test.emp.service.EmpService;
import com.example.test.emp.vo.EmpReq;
import com.example.test.emp.vo.EmpRes;
import com.example.test.emp.vo.FileVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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
        try {
            // 실패(99)
            String code = "99";
            // 회원가입 로직 구현
            log.debug("회원가입 {}", empDTO);
            // 파일 업로드 처리
            if (!file.isEmpty()) {
                empService.signUploadFile(file);
            }
            // res 성공적으로 등록(00) 여부 판단
            int res = empService.insertUser(empDTO);
            if (res > 0) {
                code = "00";
            }
            // ResponseEntity -> http에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스
            return ResponseEntity.ok(code); // 코드 반환 00,99
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사원 정보 검색
     * post
     *
     * @RequestBody : 요청 본문
     */
    @Operation(summary = "사원 정보 검색", description = "사원 정보 검색 API")
    @PostMapping("/empList/search")
    public ResponseEntity<EmpRes> getEmpListAndSearch(@RequestBody EmpReq req) throws Exception {
        EmpRes resultEmpRes = empService.getEmpListAndSearch(req);
        return ResponseEntity.ok(resultEmpRes);
    }

    /**
     * 사원목록
     * update
     */
/*    @Operation(summary = "사원 프로필 업데이트", description = "사원 프로필을 업데이트하는 API")
    @PutMapping("/update")
    public ResponseEntity<String> setEmplistUpdate(@Valid @RequestBody EmpDTO empDTO) {

        empService.empListUpdate(empDTO);
        return ResponseEntity.ok("업데이트");
    }*/
    @Operation(summary = "사원 프로필 업데이트", description = "사원 프로필을 업데이트하는 API")
    @PutMapping("/update")
    public ResponseEntity<String> setEmplistUpdate(@Valid @RequestBody EmpDTO empDTO) {
        int result = empService.empListUpdate(empDTO);

        if (result > 0) {
            return ResponseEntity.ok("프로필이 성공적으로 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 업데이트에 실패했습니다.");
        }
    }

    /**
     * 파일
     * upload
     * isEmpty = 문자열 0인경우 true 리턴
     * IOException : throw(던지다)된 예외에 대한 기본 클래스
     * RequestParam - 요청 매개변수
     * RequestPart -  multipart/form-data에 특화된 어노테이션
     * setAttribute - 태그 속성 추가 하기
     */
    @Operation(summary = "파일 업로드", description = "파일 업로드")
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            FileVo fileVo = empService.uploadFile(file);
            request.setAttribute("fileVo", fileVo);
            return "success-page";
        } catch (IOException e) {
            request.setAttribute("error", "파일 업로드 중 오류가 발생했습니다.");
            return "error-page";
        }
    }
}

