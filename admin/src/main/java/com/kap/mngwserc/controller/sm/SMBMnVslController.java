package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMBMainVslAdmDTO;
import com.kap.core.dto.SMBMainVslDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMBMnVslService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/sm/smb/{gubun:pc|mobile}")
public class SMBMnVslController {

    private final SMBMnVslService sMBMnVslService;

    /**
     * 메인 비주얼 목록 페이지
     */
    @RequestMapping(value = "/list")
    public String getMnVslListPage(SMBMainVslAdmDTO sMBMainVslAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception {
        try {
            sMBMainVslAdmDTO.setDvcCd(gubun);
            sMBMainVslAdmDTO.setLangCd(langCd);
            modelMap.addAttribute("langCd", langCd);
            modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslAdmDTO));
            modelMap.addAttribute("gubun", gubun);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslList.admin";
    }

    /**
     * 메인 비주얼 목록 조회
     */
    @RequestMapping(value = "/select")
    public String selectMnVslList(SMBMainVslAdmDTO sMBMainVslAdmDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception {
        try {
            sMBMainVslAdmDTO.setDvcCd(gubun);
            modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslAdmDTO));
            modelMap.addAttribute("gubun", gubun);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslListAjax";
    }

    /**
     * 메인 비주얼 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getMnVslWritePage(SMBMainVslAdmDTO sMBMainVslAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslAdmDTO.setDvcCd(gubun);
            modelMap.addAttribute("gubun", sMBMainVslAdmDTO.getDvcCd());

            if (!"".equals(sMBMainVslAdmDTO.getDetailsKey()) && sMBMainVslAdmDTO.getDetailsKey() != null) {
                modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslDtl(sMBMainVslAdmDTO));
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslWrite.admin";
    }

    /**
     * 메인 비주얼 등록
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insertMnVsl(SMBMainVslAdmDTO sMBMainVslAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslAdmDTO.setDvcCd(gubun);
            sMBMainVslAdmDTO.setRegId(coaAdmDTO.getId());
            sMBMainVslAdmDTO.setRegIp(coaAdmDTO.getLoginIp());
            modelMap.addAttribute("respCnt", sMBMnVslService.insertMnVsl(sMBMainVslAdmDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateMnVsl(SMBMainVslAdmDTO sMBMainVslAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslAdmDTO.setDvcCd(gubun);
            sMBMainVslAdmDTO.setRegId(coaAdmDTO.getId());
            sMBMainVslAdmDTO.setRegIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", sMBMnVslService.updateMnVsl(sMBMainVslAdmDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 메인 비주얼 삭제
     */
    @PostMapping(value = "/delete")
    public String deleteMnVsl(SMBMainVslAdmDTO sMBMainVslAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslAdmDTO.setRegId(coaAdmDTO.getId());
            sMBMainVslAdmDTO.setRegIp(coaAdmDTO.getLoginIp());
            sMBMainVslAdmDTO.setRegName(coaAdmDTO.getName());
            sMBMainVslAdmDTO.setRegDeptNm(coaAdmDTO.getDeptNm());
            sMBMainVslAdmDTO.setModId(coaAdmDTO.getId());
            sMBMainVslAdmDTO.setModIp(coaAdmDTO.getLoginIp());
            sMBMainVslAdmDTO.setDvcCd(gubun);
            sMBMainVslAdmDTO.setLangCd(langCd);

            modelMap.addAttribute("respCnt", sMBMnVslService.deleteMnVsl(sMBMainVslAdmDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 메인 비주얼 사용 여부 수정
     */
    /*@PostMapping(value = "/use-yn-update")
    public String updateUseYn(SMBMainVslDTO pSMBMainVslDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            pSMBMainVslDTO.setRegId(coaAdmDTO.getId());
            pSMBMainVslDTO.setRegIp(coaAdmDTO.getLoginIp());
            pSMBMainVslDTO.setRegName(coaAdmDTO.getName());
            pSMBMainVslDTO.setRegDeptNm(coaAdmDTO.getDeptNm());
            pSMBMainVslDTO.setModId(coaAdmDTO.getId());
            pSMBMainVslDTO.setModIp(coaAdmDTO.getLoginIp());
            pSMBMainVslDTO.setDvcCd(gubun);
            pSMBMainVslDTO.setLangCd(langCd);

            modelMap.addAttribute("respCnt", sMBMnVslService.updateUseYn(pSMBMainVslDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }*/

    /**
     * 메인 비주얼 정렬 수정
     */
    @RequestMapping(value="/sort", method=RequestMethod.POST)
    public ModelAndView updateOrder(SMBMainVslAdmDTO sMBMainVslAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception {

        ModelAndView mav = new ModelAndView();

        try {
            modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslAdmDTO));
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslAdmDTO.setModId(coaAdmDTO.getId());
            sMBMainVslAdmDTO.setModIp(coaAdmDTO.getLoginIp());
            sMBMainVslAdmDTO.setDvcCd(gubun);

            sMBMnVslService.updateOrder(sMBMainVslAdmDTO);
            mav.setViewName("jsonView");

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return mav;
    }
}