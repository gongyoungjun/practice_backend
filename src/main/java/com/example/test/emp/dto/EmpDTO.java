package com.example.test.emp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Schema(name = "EmpDTO", description = "사원 테이블")
public class EmpDTO {
    @Schema(description = "사번")
    private int empNo;
    @Schema(description = "사원이름")
    @Size(min = 2, max = 10, message = "사원이름은 2~10자로 입력하세요")
    @NotNull(message = "사원이름은 필수입니다.")
    private String empNm;
    @Schema(description = "비밀번호")
    @NotNull(message = "비밀번호는 필수입니다.")
    @Size(min = 4, max = 16, message = "비밀번호는 4~16자로 입력하세요.")
    private String empPwd;
    @Pattern(regexp = "\\d{2,3}\\d{3,4}\\d{4}", message = "ex)01012341234")
    @Schema(description = "전화번호")
    private String empPhn;
    @Schema(description = "이메일")
    private String empEml;
    @Schema(description = "사원상태 코드")
    private String empStCd;
    @Schema(description = "직급 코드")
    private String empRnkCd;
    @Schema(description = "입사일")
    private LocalDate empHrDt;
    @Schema(description = "연봉")
    private int empSlr;
    @Schema(description = "생년월일")
    private LocalDate empBrtDt;
    @Schema(description = "이미지 이름")
    private String imgNm;
    @Schema(description = "이미지 파일")
    private MultipartFile file;


}