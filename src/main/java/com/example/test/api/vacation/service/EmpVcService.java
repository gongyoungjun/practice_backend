package com.example.test.api.vacation.service;

import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.vacation.vo.VacationDTO;
import com.example.test.api.vacation.vo.VcCreate;
import com.example.test.api.vacation.vo.VcReq;

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
    int insertVacation(VcCreate vcCreate) throws Exception;

    /**
     * 휴가
     * 상세 페이지
     */
    VacationDTO getVcDetail(int vctnNo);

    /**
     * 휴가
     * 승인
     */
    VacationDTO approveVc(VcReq req);

}
