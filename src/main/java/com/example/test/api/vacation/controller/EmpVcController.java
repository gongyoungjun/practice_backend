package com.example.test.api.vacation.controller;

import com.example.test.api.config.Code;
import com.example.test.api.vacation.service.EmpVcService;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.vacation.vo.VacationDTO;
import com.example.test.api.vacation.vo.VcCreate;
import com.example.test.api.vacation.vo.VcReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class EmpVcController {

    private final EmpVcService empVcService;


    /**
     * 휴가
     * 목록 조회
     */
    @Operation(summary = "휴가 목록 조회", description = "휴가 목록 조회 API")
    @PostMapping("/vacation/list")
    public ResponseEntity<EmpRes> getEmpListAndSearch(@RequestBody EmpReq req) {
        EmpRes res = new EmpRes();
        String code = Code.SUCCESS;
        try {
            res = empVcService.getVacation(req);
        } catch (Exception e) {
            code = Code.FAIL;
        }
        res.setCode(code);
        return ResponseEntity.ok(res);
    }


    /**
     * 휴가
     * 신청
     */
    @Operation(summary = "휴가 신청", description = "휴가 신청")
    @PostMapping("/request")
    public ResponseEntity<EmpRes> createVacation(@Valid @RequestBody VcCreate vcCreate) {
        EmpRes res = new EmpRes();
        String code = Code.FAIL;

        try {
            int result = empVcService.createVacation(vcCreate);
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
     * 휴가
     * 신청
     */
    @Operation(summary = "휴가 승인,취소", description = "휴가 승인,취소")
    @PostMapping("/approve")
    public ResponseEntity<VacationDTO> approveVacation(@RequestBody VcReq req) {
        VacationDTO approvedVacation = empVcService.approveVc(req);
        return ResponseEntity.ok(approvedVacation);
    }

}
