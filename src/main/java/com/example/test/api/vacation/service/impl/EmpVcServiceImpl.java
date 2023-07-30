package com.example.test.api.vacation.service.impl;

import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.vacation.dao.VctnDao;
import com.example.test.api.vacation.service.EmpVcService;
import com.example.test.api.vacation.vo.VacationDTO;
import com.example.test.api.vacation.vo.VcCreate;
import com.example.test.api.vacation.vo.VcReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmpVcServiceImpl implements EmpVcService {

    private final VctnDao vctnDao;
    /**
     * 휴가
     * 목록 조회
     */
    @Override
    public EmpRes getVacation(EmpReq req) {
        List<VacationDTO> vcList = null;

        EmpRes res = new EmpRes();
        res.setPage(req.getPage());
        res.setListSize(req.getListSize());

        int totalCount = vctnDao.selectBoardListCnt(req);

        if (totalCount > 0) {
            vcList = vctnDao.getVcEmpNo(req);
        }
        res.setListCnt(totalCount);
        res.setVacationList(vcList);

        return res;
    }

    /**
     * 휴가
     * 신청
     */
    @Override
    public int createVacation(VcCreate vcCreate) {
        return vctnDao.createVacation(vcCreate);
    }

    /**
     * 휴가
     * 승인
     */
    @Override
    public VacationDTO approveVc(VcReq req) {
        VacationDTO vacationDTO = vctnDao.getVacation(req);
        if (vacationDTO != null) {
            vacationDTO.setVctnStNm(req.getVctnStNm());
            vctnDao.updateVacationStatus(vacationDTO);
        }
        return vacationDTO;
    }



}
