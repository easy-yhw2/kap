package com.kap.mngwserc.controller;

import com.kap.core.annotation.MapData;
import com.kap.core.dto.*;
import com.kap.service.COFileService;
import com.kap.service.COMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <pre> 
 * 샘플 Controller
 * </pre>
 *
 * @ClassName		: COCAdmController.java
 * @Description		: 관리자 관리를 위한 Controller
 * @author 허진영
 * @since 2020.10.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2020.10.22		허진영				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/send")
public class COASmpleMessageController {

    /** 메일 서비스 **/
    private final COMessageService cOMessageService;
    /** 파일 서비스 **/
    private final COFileService cOFileService;

    /**
     * <pre>
     * 샘플 API Controller
     * </pre>
     *
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2020.10.20			허진영			 		최초생성
     * </pre>
     */
    @RestController
    @RequestMapping(value="/send")
    public class COASmpleRestController {
        /**
         * 샘플 이메일 발송
         */
        @GetMapping(value="/email")
        public HashMap<String, Object> setEmailSend(ModelMap modelMap, HttpServletRequest request, @MapData EmfMap emfMap) throws Exception
        {
            //메일 발송
            COMailDTO mailForm = new COMailDTO();
            mailForm.setSubject("[ KAP ] 문의에 대한 답변 드립니다");
            //메일 발송할 첨부파일 처리(샘플)
            List<COFileDTO> uploadFiles = cOFileService.getFileInfs(952);
            if (uploadFiles != null && uploadFiles.size() > 0) {
                mailForm.setFileList(uploadFiles);
            }
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setEmail("dldhrwjd@easymedia.net ");
            //이름(비필수)
            receiverDto.setName("이옥정");
            //치환문자1
            receiverDto.setNote1("치환문자1");
            //치환문자2
            receiverDto.setNote2("치환문자2");
            //치환문자3
            receiverDto.setNote3("치환문자3");
            //치환문자4
            receiverDto.setNote4("치환문자4");
            //치환문자5
            receiverDto.setNote5("치환문자5");
            //수신자 정보 등록
            mailForm.getReceiver().add(receiverDto);
            return cOMessageService.sendMail(mailForm, "IMAQaRply.html");
        }

        /**
         * 샘플 SMS 발송
         */
        @GetMapping(value="/sms")
        public HashMap<String, Object> setSmsSend(ModelMap modelMap, HttpServletRequest request, @MapData EmfMap emfMap) throws Exception
        {
            //메일 발송
            COSmsDTO smsDto = new COSmsDTO();
            smsDto.setSubject("[ KAP ] 문의에 대한 답변 드립니다");
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setMobile("01033746727");
            //이름(비필수)
            receiverDto.setName("이옥정");
            //치환문자1
            receiverDto.setNote1("치환문자1");
            //치환문자2
            receiverDto.setNote2("치환문자2");
            //치환문자3
            receiverDto.setNote3("치환문자3");
            //치환문자4
            receiverDto.setNote4("치환문자4");
            //치환문자5
            receiverDto.setNote5("치환문자5");
            //수신자 정보 등록
            smsDto.getReceiver().add(receiverDto);
            return cOMessageService.sendSms(smsDto, "SMSSample.txt");
        }
    }
}