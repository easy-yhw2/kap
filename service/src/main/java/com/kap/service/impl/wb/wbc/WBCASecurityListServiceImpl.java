package com.kap.service.impl.wb.wbc;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbc.WBCBCompanyDTO;
import com.kap.core.dto.wb.wbc.WBCBSecuritySearchDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBCASecurityListService;
import com.kap.service.dao.COGCntsMapper;
import com.kap.service.dao.wb.wbc.WBCASecurityListMapper;
import com.kap.service.dao.wb.wbc.WBCBSecurityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBCASecurityListServiceImpl implements WBCASecurityListService {


    //Mapper
    private final WBCBSecurityMapper wBCBSecurityMapper;
    private final WBCASecurityListMapper wBCASecurityListMapper;
    private final COGCntsMapper cOGCntsMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /* 상생사업관리 지급 시퀀스 */
    private final EgovIdGnrService cxEpisdGiveSeqIdgen;

    /**
     * 회차 목록
     */
    public WBRoundMstSearchDTO selectCarbonList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(wBRoundMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBRoundMstSearchDTO.getListRowSize());
        page.setPageSize(wBRoundMstSearchDTO.getPageRowSize());

        if ("admin".equals(wBRoundMstSearchDTO.getSiteGubun())) {
            wBRoundMstSearchDTO.setFirstIndex(page.getFirstRecordIndex());
            wBRoundMstSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }
        wBRoundMstSearchDTO.setList(wBCASecurityListMapper.selectCarbonList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBCASecurityListMapper.getCarbonListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 등록
     */
    public int insertCarbon(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);
        
        respCnt = wBCASecurityListMapper.insertCarbon(wBRoundMstDTO);

        for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){

            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

            wBOrderMstDto.setEpisdSeq(firstEpisdSeqIdgen);
            wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);

            wBCASecurityListMapper.insertGiveList(wBOrderMstDto);
        }

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    public int updateCarbon(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        String detailsKey = wBRoundMstDTO.getDetailsKey();
        int firstEpisdGiveSeqIdgen = 0;

        if(wBRoundMstDTO.getGiveList().size() > 0){
            wBCASecurityListMapper.deleteGiveList(wBRoundMstDTO);

            for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){
                firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

                WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

                wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);
                wBOrderMstDto.setDetailsKey(detailsKey);

                wBCASecurityListMapper.updateGiveList(wBOrderMstDto);
            }
            respCnt = wBCASecurityListMapper.updateCarbon(wBRoundMstDTO);
        }else{
            respCnt = wBCASecurityListMapper.updateExpsYnCarbon(wBRoundMstDTO);
        }

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteCarbon(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBCASecurityListMapper.deleteCarbon(wBRoundMstDTO);
        wBCASecurityListMapper.deleteGiveList(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectCarbonDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBCASecurityListMapper.selectCarbonDtl(wBRoundMstSearchDTO);

        wBRoundMstDTO.setDetailsKey(detailsKey);
        wBRoundMstDTO.setGiveList(wBCASecurityListMapper.selectGiveList(wBRoundMstDTO));

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        return wBCASecurityListMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 매핑 여부 확인
     */
    public int getAppctnCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBCASecurityListMapper.getAppctnCnt(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int carbonDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBCASecurityListMapper.carbonDeleteList(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 회차 중복 체크
     */
    public int episdChk(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBCASecurityListMapper.episdChk(wBRoundMstDTO);
        return respCnt;
    }

    /**
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        wBRoundMstSearchDTO = wBCASecurityListMapper.getRoundDtl(wBRoundMstSearchDTO);

        if (wBRoundMstSearchDTO != null) {
            List<SMJFormDTO> smjList = wBCASecurityListMapper.selectOPtnList(wBRoundMstSearchDTO);
            wBRoundMstSearchDTO.setSmjList(smjList);
        }

        return wBRoundMstSearchDTO;
    }

    /**
     * 최신 회차 상세 조회
     */
    public int getApplyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        int rtnCode = 0;

        COUserDetailsDTO cOUserDetailsDTO = null;

        if (!COUserDetailsHelperService.isAuthenticated())
        {
            //비로그인 코드 100
            rtnCode = 999;
        }
        else
        {
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

            if (!"CP".equals(cOUserDetailsDTO.getAuthCd())) {
                if ("CS".equals(cOUserDetailsDTO.getAuthCd())) {
                    //위원인 경우 150
                    rtnCode = 150;
                } else {
                    //부품사회원이 아닌경우 100
                    rtnCode = 100;
                }
            } else if ("CP".equals(cOUserDetailsDTO.getAuthCd())) {

                WBCBSecuritySearchDTO wBCBSecuritySearchDTO = new WBCBSecuritySearchDTO();
                wBCBSecuritySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                WBCBCompanyDTO wBCBCompanyDTO = wBCBSecurityMapper.getCompanyInfo(wBCBSecuritySearchDTO);
                if ("COMPANY01001".equals(wBCBCompanyDTO.getCtgryCd()) || "COMPANY01002".equals(wBCBCompanyDTO.getCtgryCd())) {
                    wBRoundMstSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());

                    //신청가능 코드 200
                    rtnCode = 200;
                    RequestContextHolder.getRequestAttributes().setAttribute("contentAuth", wBRoundMstSearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
                }
            } else {
                //부품사가 1차 2차가 아닐떄,
                rtnCode = 190;
            }
        }

        return rtnCode;
    }
}
