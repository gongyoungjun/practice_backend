package com.example.test.api.vacation.controller;

import com.example.test.api.config.Code;
import com.example.test.api.vacation.service.EmpVcService;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.vacation.vo.VacationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @PostMapping("/requests/search")
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
    public ResponseEntity<EmpRes> createVacation(@RequestBody VacationDTO vacationDTO) {
        EmpRes res = new EmpRes();
        String code = Code.FAIL;

        try {
            List<VacationDTO> list = empVcService.createVacation(vacationDTO);
            if (list != null && !list.isEmpty()){
                code = Code.SUCCESS;
            }
            res.setVacationList(list);
            res.setCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(res);
    }


/*    @PostMapping("/approve/{vacationRequestId}")

    public ResponseEntity<EmpRes> approveVacationRequest(@PathVariable int vacationRequestId) {
        boolean isApproved = empVcService.approveVacation(vacationRequestId);
        EmpRes res = new EmpRes();
        res.setCode(isApproved ? Code.SUCCESS : Code.FAIL);
        return ResponseEntity.ok(res);
    }*/
}
