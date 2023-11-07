package com.kap.mngwserc.controller.em;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.EBACouseDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBACouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 교육과정관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육과정관리를 위한 Controller
 * @author 김학규
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/eb/eba")
public class EBACouseController {

    /** 서비스 **/
    public final EBACouseService eBACouseService;

    public final COCodeService cOCodeService;

    /**
     *  교육과정관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCouseList(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("CLASS_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("CLASS01");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS02");
        modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS03");
        modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

        //cOCodeDTO.setCd("CLASS03003");
        //List<COCodeDTO> tempList = cOCodeService.getCdIdPrntList(cOCodeDTO);

        return "mngwserc/eb/eba/EBACouseList.admin";
    }



    /**
     * 교육과정관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCouseListPageAjax(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/eba/EBACouseListAjax";
    }


    /**
     * 교육과정관리  상세를 조회한다.
     */
    @GetMapping(value="/write")
    public String getCouseDtl(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        modelMap.addAttribute("langCd", langCd);
        modelMap.addAttribute("rtnData", eBACouseService.selectCouseDtl(eBACouseDTO));

        return "mngwserc/eb/eba/EBACouseWrite.admin";
    }


    /**
     * 교육과정관리를 등록한다.
     */
    @GetMapping(value="/insert")
    public EBACouseDTO getCouseInsert(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setRespCnt( eBACouseService.insertCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }

    /**
     * 교육과정관리를 수정한다.
     */
    @GetMapping(value="/update")
    public EBACouseDTO getCouseUpdate(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setRespCnt( eBACouseService.updateCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }

    /**
     * 교육과정관리를  삭제한다.
     */
    @GetMapping(value="/delete")
    public EBACouseDTO getCouseDelete(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setRespCnt( eBACouseService.deleteCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }


    /**
     * 교육과정관리를 복사한다.
     */
    @GetMapping(value="/copy")
    public EBACouseDTO getCouseCopy(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setRespCnt( eBACouseService.copyCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }

    /**
     * 교육과정 검색 팝업
     */



    /**
     * 교육과정 회차정보 조회
     */
    @PostMapping(value="/sessionList")
    public String getSessionList(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        modelMap.addAttribute("langCd", langCd);
        modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));

        return "mngwserc/eb/eba/EBACouseSessionListAjax";
    }

    /**
     * 교육과정 분류 3뎁스 호출
     */
    @RequestMapping(value = "/classTypeList")
    public String setEthicUser(COCodeDTO cOCodeDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {

        try
        {

            cOCodeDTO.setCdId("CLASS_TYPE");
            //cOCodeDTO.setCd("CLASS01");
            cOCodeDTO.setDpth(3);
            List<COCodeDTO> tempList2 = cOCodeService.getCdIdList(cOCodeDTO);

            for(COCodeDTO a: tempList2){
                System.out.println("@@@@ 포함 = " + a);
            }

            modelMap.addAttribute("detailList", cOCodeService.getCdIdList(cOCodeDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/eba/EBACouseCdListAjax";
    }












}

