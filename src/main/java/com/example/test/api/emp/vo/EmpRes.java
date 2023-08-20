package com.example.test.api.emp.vo;

import com.example.test.api.emp.dto.EmpCommuteDTO;
import com.example.test.api.kakao.dto.KakaoTokenDTO;
import com.example.test.api.vacation.vo.VacationDTO;
import com.example.test.api.emp.dto.EmpDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.annotations.Flush;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmpRes extends PageParam {

    @Schema(description = "쿼리")
    private String query;
    @Schema(description = "데이터")
    private Object data;
    @Schema(description = "응답 코드")
    private String code;
    @Schema(description = "로그인 토큰")
    private String token;
    @Schema(description = "리스트 시작 위치")
    private int startList;
    @Schema(description = "사원 목록")
    private List<EmpDTO> list;
    @Schema(description = "휴가 목록")
    private List<VacationDTO> vacationList;
    @Schema(description = "출근 목록")
    private List<EmpCommuteDTO> empCommuteDTO;
    @Schema(description = "카카오 토큰")
    private String accessToken;
    @Schema(description = "인가 코드")
    private String authCode;
    @Schema(description = "카카오 토큰")
    private List<KakaoTokenDTO> kakaList;
}
