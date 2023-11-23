package com.kap.mngwserc.controller.mp;

import com.kap.common.utility.CODateUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.MPAAttctnDto;
import com.kap.core.dto.MPAInqrDto;
import com.kap.core.dto.MPAUserDto;
import com.kap.service.COMailService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * <pre>
 * 일반회원관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 일반회원관리를 위한 Controller
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mpa")
public class MPAUserController {

    private final MPAUserService mpaUserService;

    @Value("${app.site.name}")
    private String siteName;
    //사이트 웹 소스 URL
    @Value("${app.user-domain}")
    private String httpFrontUrl;
    //사이트 관리자 URL
    @Value("${app.admin-domain}")
    private String httpAdmtUrl;

    //이메일 발송
    private final COMailService cOMailService;

    String tableNm = "MEM_MOD_SEQ";

    /**
     * 일반회원관리 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getUserListPage(MPAUserDto mpaUserDto ,
                                  ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CO");
        mpaUserDto.setExcelYn("N");
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpa/MPAUserList.admin";
    }

    /**
     * 일반회원관리 목록으로 이동한다.
     */
    @PostMapping(value = "/select")
    public String selectUserListPageAjax(MPAUserDto mpaUserDto ,
                                         ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CO");
        mpaUserDto.setExcelYn("N");
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpa/MPAUserListAjax";
    }

    /**
     * 미래차공모전 리스트 조회
     */
    @PostMapping(value = "/select-tab-two")
    public String selectUserListPageTabTwoAjax(MPAAttctnDto mpaAttctnDto ,
                                               ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpaUserService.selectAttcntList(mpaAttctnDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaAttctnDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpa/MPAUserTabTwoAjax";
    }

    /**
     * 문의 내역 리스트 조회
     */
    @PostMapping(value = "/select-tab-three")
    public String selectUserListPageTabThreeAjax(MPAInqrDto mpaInqrDto ,
                                                 ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpaUserService.selectInqrList(mpaInqrDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaInqrDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpa/MPAUserTabThreeAjax";
    }

    /**
     * 관리자 상세 페이지
     */
    @RequestMapping(value="/write")
    public String getAdmWritePage(MPAUserDto mpaUserDto ,
                                  ModelMap modelMap ) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CO");

            // 로그인한 계정
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
            modelMap.addAttribute("rtnData", mpaUserDto);


            if(!"".equals(mpaUserDto.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", mpaUserService.selectUserDtl(mpaUserDto));
            }
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/mp/mpa/MPAUserWrite.admin";
    }

    /**
     * 이메일 중복 체크
     */
    @PostMapping(value="/dup-email")
    public String selectDupEmail(MPAUserDto mpaUserDto ,
                                 ModelMap modelMap ) throws Exception
    {
        try
        {
            // 로그인한 계정
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());

            String chk;
            if(mpaUserService.selectDupEmail(mpaUserDto) >=1) {
                chk = "N";
            } else {
                chk = "Y";
            }
            modelMap.addAttribute("dupChk", chk);

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

    /**
     * id 중복 체크
     */
    @PostMapping(value="/dup-id")
    public String selectDupId(MPAUserDto mpaUserDto ,
                                 ModelMap modelMap ) throws Exception
    {
        try
        {

            // 로그인한 계정
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());

            String chk;
            if(mpaUserService.selectDupId(mpaUserDto) >=1) {
                chk = "N";
            } else {
                chk = "Y";
            }
            modelMap.addAttribute("dupChk", chk);

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


    /**
     * 일반사용자  상세 페이지
     */
    @PostMapping(value="/select-tab-one")
    public String getUserDtlAjax(MPAUserDto mpaUserDto ,
                             ModelMap modelMap ) throws Exception
    {
        try
        {
            // 로그인한 계정
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());

            if(!"".equals(mpaUserDto.getDetailsKey())){
                modelMap.addAttribute("rtnDtl", mpaUserService.selectUserDtlTab(mpaUserDto));
            }
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return  "mngwserc/mp/mpa/MPAUserTabOneAjax";
    }

    /**
     * 사용자 수정
     */
    @PostMapping(value="/update")
    public String insertPartsCompany(MPAUserDto mpaUserDto ,
                                     ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setModCd("AD"); //임의 관리자 cd
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
            mpaUserDto.setRegId( lgnCOAAdmDTO.getId() );
            mpaUserDto.setRegIp( lgnCOAAdmDTO.getLoginIp() );
            mpaUserDto.setModId( lgnCOAAdmDTO.getId() );
            mpaUserDto.setTableNm(tableNm);
            modelMap.addAttribute("respCnt", mpaUserService.updateUserDtl(mpaUserDto));
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


    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(MPAUserDto mpaUserDto ,
                                    HttpServletResponse response) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CO");
            mpaUserDto.setExcelYn("Y");
            //엑셀 생성
            mpaUserService.excelDownload(mpaUserService.selectUserList(mpaUserDto), response);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 비밀번호 초기화
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping(value="/pwd-init")
    public String updatePwdInit(@RequestParam("id") String id) throws Exception
    {

        MPPwdInitDto mpPwdInitDto = new MPPwdInitDto();
        int actCnt = 0;
        try
        {

            mpPwdInitDto.setId(id);
            actCnt = mpaUserService.updatePwdInit(mpPwdInitDto);



            if (actCnt > 0)
            {
                //이메일 발송
                COMailDTO cOMailDTO = new COMailDTO();
                cOMailDTO.setSubject("["+siteName+"] 임시비밀번호 발급 안내");
                cOMailDTO.setSiteName(siteName);
                cOMailDTO.setHttpFrontUrl(httpFrontUrl);
                cOMailDTO.setHttpAdmUrl(httpAdmtUrl);
                cOMailDTO.setEmails(mpPwdInitDto.getEmail());
                cOMailDTO.setField1(mpPwdInitDto.getPwd());
                //인증요청일시
                String field2 = CODateUtil.convertDate(CODateUtil.getToday("yyyyMMddHHmm"),"yyyyMMddHHmm", "yyyy-MM-dd HH:mm", "");
                cOMailDTO.setField2(field2);
                if(mpPwdInitDto.getMemCd().equals("CO")) {
                    cOMailDTO.setField3("일반 사용자");
                } else if(mpPwdInitDto.getMemCd().equals("CP")) {
                    cOMailDTO.setField3("부품 사회원");
                } else if(mpPwdInitDto.getMemCd().equals("CS")) {
                    cOMailDTO.setField3("위원");
                }
                cOMailService.sendMail(cOMailDTO, "UserPwdInit.html");
            }
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