package com.example.test.api.emp.controller;

import com.example.test.api.emp.service.EmpService;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.config.Code;
import com.example.test.api.emp.dto.EmpDTO;
import com.example.test.api.emp.vo.LessonException;
import com.example.test.api.emp.vo.LessonRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
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

    private final MessageSourceAccessor MessageSourceAccessor;

    /**
     * 회원가입
     * PostMapping - consuemes
     */
    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<String> insertUser(@RequestPart("file") MultipartFile file, @RequestBody EmpDTO empDTO) {
        try {
            // 실패(99)
            String code = Code.FAIL;
            // 회원가입 로직 구현
            log.debug("회원가입 {}", empDTO);
            // 파일 업로드 처리
            if (!file.isEmpty()) {
                empService.signUploadFile(file);
            }
            // res 성공적으로 등록(00) 여부 판단
            int res = empService.insertUser(empDTO);
            if (res > 0) {
                code = Code.SUCCESS;
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
    @Operation(summary = "사원 프로필 업데이트", description = "사원 프로필을 업데이트하는 API")
    @PutMapping("/update")
    public ResponseEntity<String> setEmplistUpdate(@Valid @RequestBody EmpDTO empDTO) {
        int result = empService.empListUpdate(empDTO);

        if (result > 0) {
            return ResponseEntity.ok(MessageSourceAccessor.getMessage("profileSuccess"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageSourceAccessor.getMessage("profileFail"));
        }
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
     *
     * @PathVariable = 템플릿 변수 처리 {}
     */
    @Operation(summary = "Lesson", description = "Lesson")
    @GetMapping("/read-file/{fileName}")
    public ResponseEntity<LessonRes> readFile(@PathVariable String fileName) {
        String code = Code.FAIL;
        ;
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
        return ResponseEntity.ok(lessonRes);
    }


}

