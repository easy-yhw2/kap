package com.kap.front.controller.mp.mpb;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpb.MPBBnsMstDTO;
import com.kap.core.dto.mp.mpb.MPBBnsSearchDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyDtlDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanyDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbh.WBHACalibrationSearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 홈 > 마이페이지 > 상생 사업 신청내역 Controller
 * </pre>
 *
 * @ClassName		: MPBCoexistenceController.java
 * @Description		: 마이페이지 상생 사업 신청내역 Controller
 * @author 김태훈
 * @since 2024.01.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.08		김태훈				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/coexistence")
public class MPBCoexistenceController {

    /* 서비스 */
    private final COCodeService cOCodeService;
    public final MPBCoexistenceService mpbCoexistenceService;
    public final WBBBCompanyService wbbbCompanyService;
    public final WBHACalibrationService wbhaCalibrationService;
    public final WBIBSupplyCompanyService wBIBSupplyCompanyService;
    public final WBJBAcomListService wBJBAcomListService;


    /**
     * 마이페이지 상생 사업 신청내역 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getCoexistenceList(MPBBnsSearchDTO mpbBnsSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {

        modelMap.addAttribute("rtnData", mpbCoexistenceService.selectApplyList(mpbBnsSearchDTO,"init"));

        return "front/mp/mpb/MPBCoexistenceList.front";
    }

    /**
     * 마이페이지 상생 사업 신청내역 더보기
     */
    @RequestMapping(value = "/addRoundMore")
    public String addRoundMore(MPBBnsSearchDTO mpbBnsSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("rtnData", mpbCoexistenceService.selectApplyList(mpbBnsSearchDTO,"add"));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpb/MPBCoexistenceListAjax";
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/view")
    public String getCoexistenceView(MPBBnsSearchDTO mpbBnsSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {

        String vwUrl = "front/mp/mpb/MPBCoexistenceView.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpbBnsSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
            mpbBnsSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            RequestContextHolder.getRequestAttributes().setAttribute("appctnSeq", mpbBnsSearchDTO.getAppctnSeq(), RequestAttributes.SCOPE_SESSION);

            //공통사업 + 각사업에 대한 페이지 서비스 분기
            //공통사업 여부
            String businessYn = mpbCoexistenceService.getBusinessYn(mpbBnsSearchDTO);

            if("Y".equals(businessYn)) {
                /*TO-DO 공통사업 진행*/
                WBBACompanySearchDTO wbbaCompanySearchDTO = new WBBACompanySearchDTO();
                wbbaCompanySearchDTO.setBsnCd(mpbBnsSearchDTO.getBsnCd());
                wbbaCompanySearchDTO.setDetailsKey(String.valueOf(mpbBnsSearchDTO.getAppctnSeq()));

                WBBAApplyMstDTO wbbApplyMstDTO = new WBBAApplyMstDTO();
                wbbApplyMstDTO = wbbbCompanyService.selectCompanyDtl(wbbaCompanySearchDTO);
                modelMap.addAttribute("rtnInfo", wbbApplyMstDTO);
            } else {
                if ("코드".equals(mpbBnsSearchDTO.getBsnCd())) {

                } else if ("BSN06".equals(mpbBnsSearchDTO.getBsnCd())) {
                    //스마트공장
                    WBHACalibrationSearchDTO wbhaCalibrationSearchDTO = new WBHACalibrationSearchDTO();
                    wbhaCalibrationSearchDTO.setDetailsKey(String.valueOf(mpbBnsSearchDTO.getAppctnSeq()));
                    
                } else if ("BSN08".equals(mpbBnsSearchDTO.getBsnCd())) {
                    //검교정
                    WBHACalibrationSearchDTO wbhaCalibrationSearchDTO = new WBHACalibrationSearchDTO();
                    wbhaCalibrationSearchDTO.setDetailsKey(String.valueOf(mpbBnsSearchDTO.getAppctnSeq()));
                    modelMap.addAttribute("rtnData", wbhaCalibrationService.selectCompanyDtl(wbhaCalibrationSearchDTO));
                } else if ("BSN09".equals(mpbBnsSearchDTO.getBsnCd())) {
                    //공급망
                    WBIBSupplySearchDTO wBIBSupplySearchDTO = new WBIBSupplySearchDTO();
                    wBIBSupplySearchDTO.setDetailsKey(String.valueOf(mpbBnsSearchDTO.getAppctnSeq()));
                    modelMap.addAttribute("rtnData", wBIBSupplyCompanyService.selectSupplyDtl(wBIBSupplySearchDTO));
                } else if ("BSN10".equals(mpbBnsSearchDTO.getBsnCd())) {
                    //자동차부품
                    ArrayList<String> cdDtlList = new ArrayList<String>();
                    cdDtlList.add("MEM_CD");
                    WBJAcomSearchDTO wBJAcomSearchDTO = new WBJAcomSearchDTO();
                    modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
                    wBJAcomSearchDTO.setDetailsKey(String.valueOf(mpbBnsSearchDTO.getAppctnSeq()));

                    modelMap.addAttribute("rtnData", wBJBAcomListService.selectAcomDtl(wBJAcomSearchDTO));
                    System.err.println("rtnData   :::  "  + wBJBAcomListService.selectAcomDtl(wBJAcomSearchDTO));
                }
            }

            modelMap.addAttribute("businessYn", businessYn);
            modelMap.addAttribute("rtnBsnData", mpbCoexistenceService.getBsnDetail(mpbBnsSearchDTO));
            modelMap.addAttribute("rtnCompany", mpbCoexistenceService.selectCompanyUserDtl(mpbBnsSearchDTO));
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 사업진행상황을 수정한다.
     */
    @PostMapping(value="/update")
    public String update(MPBBnsMstDTO mpbBnsMstDTO, MultipartHttpServletRequest multiRequest, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = 0;
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            
            //신청자 순번 세션체크 없으면 수정불가
            if (RequestContextHolder.getRequestAttributes().getAttribute("appctnSeq", RequestAttributes.SCOPE_SESSION) == null){
                respCnt = 100;
            }else{
                String appctnSeq = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("appctnSeq", RequestAttributes.SCOPE_SESSION));
                mpbBnsMstDTO.setAppctnSeq(Integer.valueOf(appctnSeq));

                //각 사업단계 업데이트를 위한 현재단계 조회
                MPBBnsSearchDTO mpbBnsSearchDTO = new MPBBnsSearchDTO();
                mpbBnsSearchDTO.setBsnCd(mpbBnsMstDTO.getBsnCd());
                mpbBnsSearchDTO.setAppctnSeq(mpbBnsMstDTO.getAppctnSeq());
                mpbBnsSearchDTO = mpbCoexistenceService.getBsnDetail(mpbBnsSearchDTO);

                //공통사업 + 각사업에 대한 페이지 서비스 분기
                //공통사업 여부
                String businessYn = mpbCoexistenceService.getBusinessYn(mpbBnsSearchDTO);

                if("Y".equals(businessYn)) {

                    WBBAApplyDtlDTO wbbApplyDtlDTO = new WBBAApplyDtlDTO();
                    wbbApplyDtlDTO.setBsnCd(mpbBnsSearchDTO.getBsnCd());
                    wbbApplyDtlDTO.setRsumeSeq(mpbBnsSearchDTO.getRsumeSeq());
                    wbbApplyDtlDTO.setRsumeOrd(mpbBnsSearchDTO.getRsumeOrd());
                    wbbApplyDtlDTO.setAppctnSttsCd(mpbBnsSearchDTO.getAppctnSttsCd());
                    wbbApplyDtlDTO.setAppctnSeq(mpbBnsMstDTO.getAppctnSeq());

                    respCnt = wbbbCompanyService.updateInfo(wbbApplyDtlDTO, multiRequest, request);

                } else {
                    if ("코드".equals(mpbBnsSearchDTO.getBsnCd())) {

                    } else if ("BSN08".equals(mpbBnsSearchDTO.getBsnCd())) {
                        //검교정
                    } else if ("BNS09".equals(mpbBnsSearchDTO.getBsnCd())) {
                        //공급망
                        WBIBSupplyDTO wBIBSupplyDTO = new WBIBSupplyDTO();
                        wBIBSupplyDTO.setBsnCd(mpbBnsSearchDTO.getBsnCd());
                        wBIBSupplyDTO.setRsumeSeq(mpbBnsSearchDTO.getRsumeSeq());
                        wBIBSupplyDTO.setRsumeOrd(mpbBnsSearchDTO.getRsumeOrd());
                        wBIBSupplyDTO.setAppctnSttsCd(mpbBnsSearchDTO.getAppctnSttsCd());
                        wBIBSupplyDTO.setAppctnSeq(mpbBnsMstDTO.getAppctnSeq());

                        respCnt = wBIBSupplyCompanyService.updateInfo(wBIBSupplyDTO, multiRequest, request);
                    }
                }
            }


            modelMap.addAttribute("respCnt", respCnt);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());

        }
        return "jsonView";
    }
}
