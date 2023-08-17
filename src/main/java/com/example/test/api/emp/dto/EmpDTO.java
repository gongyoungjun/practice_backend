package com.example.test.api.emp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * @Validation
 * @Length(max = 64) // 최대 길이 64
 * @NotBlank // 빈문자열은 안됨
 * @NotNull // null 안됨
 * @@NotEmpty //null "" 둘다안됨
 * @Length(max = 1_600) // 최대 길이 1,600
 */
@Data
@Schema(name = "EmpDTO", description = "사원 테이블")
public class EmpDTO {
    @Schema(description = "사번")
    private int empNo;
    @Schema(description = "사원이름")
    @Size(min = 2, max = 10, message = "사원이름은 2~10자로 입력하세요")
    @NotEmpty(message = "사원이름은 필수입니다.")
    private String empNm;
    @Schema(description = "비밀번호")
    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Size(min = 4, max = 16, message = "비밀번호는 4~16자로 입력하세요.")
    private String empPwd;
//    @Pattern(regexp = "\\d{2,3}\\d{3,4}\\d{4}", message = "ex)01012341234")
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
    @Schema(description = "권한")
    private String empAuthCd;

    @Schema(description = "sns 로그인")
    private int snsCd;
    @Schema(description = "sns 키")
    private String snsKey;


/*    @Schema(description = "이미지 이름")
    private String imgNm;
    @Schema(description = "이미지 파일")
    private MultipartFile file;*/

    /**
     * 잔여 휴가일 계산
     */
    @Schema(description = "총 휴가일 수")
    private int empVctnTtl;
    @Schema(description = "휴가 신청일 수")
    private BigDecimal vctnDayCnt;
    @Schema(description = "잔여 휴가")
    private BigDecimal vctnRsdCnt;


}