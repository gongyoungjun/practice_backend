package com.example.test.api.vacation.service;

import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.vacation.vo.VacationDTO;

import java.util.List;

public interface EmpVcService {



    /**
     * 휴가
     * 신청 목록
     */
    EmpRes getVacation(EmpReq req) throws Exception;



    /**
     * 휴가
     * 신청 등록
     */
    List<VacationDTO> createVacation(VacationDTO vacationDTO) throws Exception;
    /**
     * 휴가
     * 승인
     */

}
