package com.example.test.api.vacation.service.impl;

import com.example.test.api.emp.vo.EmpReq;
import com.example.test.api.emp.vo.EmpRes;
import com.example.test.api.vacation.dao.VctnDao;
import com.example.test.api.vacation.service.EmpVcService;
import com.example.test.api.vacation.vo.VacationDTO;
import com.example.test.api.vacation.vo.VcCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
            vcList = this.getVacationList(req);
        }
        res.setTotal(totalCount);
        res.setVacationList(vcList);

        return res;
    }

    /**
     * 휴가
     * 목록조회
     */
    private List<VacationDTO> getVacationList(EmpReq req) {
        return vctnDao.getVcEmpNo(req);
    }

    /**
     * 휴가 신청
     */
    public int insertVacation(VcCreate vcCreate) {
        try {
            // 시작과 끝 날짜를 LocalDate 객체로 변환합니다.
            LocalDate startDate = vcCreate.getVctnStrDt();
            LocalDate endDate = vcCreate.getVctnEndDt();

            // 주말을 제외한 실제 휴가일을 계산
            int vcDays = 0;
            while (!startDate.isAfter(endDate)) {
                DayOfWeek dayOfWeek = startDate.getDayOfWeek();
                if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                    vcDays++;
                }
                startDate = startDate.plusDays(1);
            }

            // 휴가 분류 코드를 확인하고 반차 또는 전체 휴가일
            if (vcCreate.getVctnKndCd().equals("02") || vcCreate.getVctnKndCd().equals("03")) {
                vcCreate.setVctnHalfCnt(BigDecimal.valueOf(vcDays * 0.5));
            } else {
                vcCreate.setVctnDayCnt(BigDecimal.valueOf(vcDays));
            }

            VacationDTO vacationDTO = selectUsedVc(vcCreate.getEmpNo());
            int remain = vacationDTO.getVctnRsdCnt().intValue();

            // DB에 저장 결과를 반환
            return vctnDao.insertVacation(vcCreate);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 휴가
     * 상세 페이지
     */
    public VacationDTO selectUsedVc(int empNo) {
        return vctnDao.selectUsedVc(empNo);
    }

    /**
     * 휴가
     * 상세 페이지
     */
    @Override
    public VacationDTO getVcDetail(int vctnNo) {
        return vctnDao.getVcDetail(vctnNo);
    }

    /**
     * 휴가
     * 승인
     */
    @Override
    public VacationDTO approveVc(VcCreate vcCreate) {
        VacationDTO vacationDTO = vctnDao.getVacation(vcCreate);

        if (vacationDTO != null) {
            vacationDTO.setVctnStCd(vcCreate.getVctnStCd());
            vctnDao.updateVc(vacationDTO);
        }
        return vacationDTO;
    }


}
