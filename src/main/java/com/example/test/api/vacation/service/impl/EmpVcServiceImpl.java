package com.example.test.api.vacation.service.impl;

import com.example.test.api.vacation.dao.VctnDao;
import com.example.test.api.vacation.service.EmpVcService;
import com.example.test.api.vacation.vo.VacationDTO;
import com.example.test.api.emp.dao.EmpDao;
import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * 목록 조회
     */
    @Override
    public List<VacationDTO> createVacation(VacationDTO vacationDTO) {
        return vctnDao.createVacation(vacationDTO);
    }

}
