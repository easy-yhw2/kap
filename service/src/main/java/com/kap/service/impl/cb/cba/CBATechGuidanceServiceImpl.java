package com.kap.service.impl.cb.cba;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.cb.cba.CBAConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceUpdateDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.sm.smi.SMISmsCntnDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.*;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * 컨설팅 기술 지도를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: CBATechGuidanceServiceImpl.java
 * @Description		: 컨설팅 기술 지도를 위한 ServiceImpl
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		임서화				   최초 생성
 *  2024.01.23      양현우             컨설팅 내역 조회(사용자 취소 제외)
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CBATechGuidanceServiceImpl implements CBATechGuidanceService {

    private final COFileService cOFileService;
    private final COFileUtil cOFileUtil;

    private final MPEPartsCompanyService mPEPartsCompanyService;

    private final MPAUserService mpaUserService;

    /* 컨설팅 기술 지도 시퀀스 */
    private final EgovIdGnrService cosultSeqIdgen;
    private final EgovIdGnrService dpndnSeqIdgen;
    private final EgovIdGnrService dlvrySeqIdgen;
    /* SQ정보 테이블 시퀀스 */
    private final EgovIdGnrService mpePartsCompanySqInfoDtlIdgen;
    private final EgovIdGnrService consTrnsfSeqIdgen;

    private final CBATechGuidanceMapper cBATechGuidanceMapper;
    private final MPEPartsCompanyMapper mpePartsCompanyMapper;

    // SMS 내용 관리 서비스
    private final SMISmsCntnService smiSmsCntnService;
    //이메일 발송
    private final COMessageService cOMessageService;

    // 로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    // 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;

    @Value("${app.file.videoExtns}")
    private String videoExtns;
    private CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO;

    @Value("${app.site.name}")
    private String siteName;

    /**
     * 컨설팅 기술 지도 목록 조회
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pCBATechGuidanceInsertDTO.getPageIndex());
        page.setRecordCountPerPage(pCBATechGuidanceInsertDTO.getListRowSize());

        page.setPageSize(pCBATechGuidanceInsertDTO.getPageRowSize());

        pCBATechGuidanceInsertDTO.setFirstIndex(page.getFirstRecordIndex());
        pCBATechGuidanceInsertDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        pCBATechGuidanceInsertDTO.setTotalCount(cBATechGuidanceMapper.selectTechGuidanceTotCnt(pCBATechGuidanceInsertDTO));
        pCBATechGuidanceInsertDTO.setList(cBATechGuidanceMapper.selectTechGuidanceList(pCBATechGuidanceInsertDTO));

        return pCBATechGuidanceInsertDTO;
    }

    /**
     * 컨설팅 기술 지도 상세 조회
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {
        this.pCBATechGuidanceInsertDTO = pCBATechGuidanceInsertDTO;
        MPEPartsCompanyDTO companyInfo = new MPEPartsCompanyDTO();

        if (!"".equals(pCBATechGuidanceInsertDTO.getDetailsKey()))
        {
            pCBATechGuidanceInsertDTO = cBATechGuidanceMapper.selectTechGuidanceDtl(pCBATechGuidanceInsertDTO);
            MPEPartsCompanyDTO mPEPartsCompanyDTO = new MPEPartsCompanyDTO();
            mPEPartsCompanyDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo().replace("-", ""));
            companyInfo = mPEPartsCompanyService.selectPartsCompanyDtl(mPEPartsCompanyDTO);

            for(int i =0; i<1; i++){
                pCBATechGuidanceInsertDTO.setCmpnNm(companyInfo.getList().get(i).getCmpnNm());
                pCBATechGuidanceInsertDTO.setRprsntNm(companyInfo.getList().get(i).getRprsntNm());
                pCBATechGuidanceInsertDTO.setStbsmDt(companyInfo.getList().get(i).getStbsmDt());
                pCBATechGuidanceInsertDTO.setCtgryCd(companyInfo.getList().get(i).getCtgryCd());
                pCBATechGuidanceInsertDTO.setBscAddr(companyInfo.getList().get(i).getBscAddr());
                pCBATechGuidanceInsertDTO.setDtlAddr(companyInfo.getList().get(i).getDtlAddr());
                pCBATechGuidanceInsertDTO.setZipcode(companyInfo.getList().get(i).getZipcode());
                pCBATechGuidanceInsertDTO.setSlsPmt(companyInfo.getList().get(i).getSlsPmt());
                pCBATechGuidanceInsertDTO.setSlsYear(companyInfo.getList().get(i).getSlsYear());
                pCBATechGuidanceInsertDTO.setMpleCnt(companyInfo.getList().get(i).getMpleCnt());
                pCBATechGuidanceInsertDTO.setMjrPrdct1(companyInfo.getList().get(i).getMjrPrdct1());
                pCBATechGuidanceInsertDTO.setMjrPrdct2(companyInfo.getList().get(i).getMjrPrdct2());
                pCBATechGuidanceInsertDTO.setMjrPrdct3(companyInfo.getList().get(i).getMjrPrdct3());
                pCBATechGuidanceInsertDTO.setCmpnTelNo(companyInfo.getList().get(i).getTelNo());

                if(pCBATechGuidanceInsertDTO.getCtgryCd().equals("COMPANY01001")) {
                    pCBATechGuidanceInsertDTO.setQlty5StarCd(companyInfo.getList().get(i).getQlty5StarCd());
                    pCBATechGuidanceInsertDTO.setQlty5StarYear(companyInfo.getList().get(i).getQlty5StarYear());
                    pCBATechGuidanceInsertDTO.setPay5StarCd(companyInfo.getList().get(i).getPay5StarCd());
                    pCBATechGuidanceInsertDTO.setPay5StarYear(companyInfo.getList().get(i).getPay5StarYear());
                    pCBATechGuidanceInsertDTO.setTchlg5StarCd(companyInfo.getList().get(i).getTchlg5StarCd());
                    pCBATechGuidanceInsertDTO.setTchlg5StarYear(companyInfo.getList().get(i).getTchlg5StarYear());
                }
            }

            for(int j=0; j<companyInfo.getList().size(); j++){
                List sqlInfoList = new ArrayList();
                sqlInfoList.add(0,companyInfo.getList().get(j).getNm());
                sqlInfoList.add(1,String.valueOf(companyInfo.getList().get(j).getScore() == null ? "" : companyInfo.getList().get(j).getScore()));
                sqlInfoList.add(2,String.valueOf(companyInfo.getList().get(j).getYear() == null ? "" : companyInfo.getList().get(j).getYear()));
                sqlInfoList.add(3,companyInfo.getList().get(j).getCrtfnCmpnNm());
                sqlInfoList.add(4,companyInfo.getList().get(j).getCbsnSeq());
                if(j==0){
                    pCBATechGuidanceInsertDTO.setSqInfoList(sqlInfoList);
                }else if(j==1){
                    pCBATechGuidanceInsertDTO.setSqInfoList1(sqlInfoList);
                }else{
                    pCBATechGuidanceInsertDTO.setSqInfoList2(sqlInfoList);
                }
            }
            int cnstgSeq = pCBATechGuidanceInsertDTO.getCnstgSeq();

            List<CBATechGuidanceInsertDTO> dlvryInfo = cBATechGuidanceMapper.selectCnstgDlvryInfo(cnstgSeq);
            List dlvryInfoList = new ArrayList();
            for(int i=0; i<dlvryInfo.size(); i++){
                CBATechGuidanceInsertDTO dlvryDto = new CBATechGuidanceInsertDTO();
                dlvryDto.setDlvryCmpnNm(dlvryInfo.get(i).getDlvryCmpnNm());
                dlvryDto.setDlvryRate(dlvryInfo.get(i).getDlvryRate());
                dlvryDto.setCmpnDlvrySeq(dlvryInfo.get(i).getCmpnDlvrySeq());
                dlvryInfoList.add(dlvryDto);
            }
            pCBATechGuidanceInsertDTO.setDlvryCmpnList(dlvryInfoList);

            List<CBATechGuidanceInsertDTO> dpndnInfo = cBATechGuidanceMapper.selectCnstgDpndnInfo(cnstgSeq);
            List dpndnInfoList = new ArrayList();
            for(int i=0; i<dpndnInfo.size(); i++){
                CBATechGuidanceInsertDTO dpndnDto = new CBATechGuidanceInsertDTO();
                dpndnDto.setDpndnCmpnNm(dpndnInfo.get(i).getDpndnCmpnNm());
                dpndnDto.setDpndnRate(dpndnInfo.get(i).getDpndnRate());
                dpndnDto.setDpndnSeq(dpndnInfo.get(i).getDpndnSeq());
                dpndnInfoList.add(dpndnDto);
            }
            pCBATechGuidanceInsertDTO.setDpndCmpnList(dpndnInfoList);

            List<CBATechGuidanceInsertDTO> appctnTypeInfo = cBATechGuidanceMapper.selectCnstgAppctnType(cnstgSeq);
            List appctnTypeList = new ArrayList();
            for(int i=0; i<appctnTypeInfo.size(); i++){
                CBATechGuidanceInsertDTO appctnDto = new CBATechGuidanceInsertDTO();
                appctnDto.setAppctnTypeCd(appctnTypeInfo.get(i).getAppctnTypeCd());
                appctnDto.setAppctnTypeNm(appctnTypeInfo.get(i).getAppctnTypeNm());
                appctnTypeList.add(appctnDto);
            }
            pCBATechGuidanceInsertDTO.setAppctnTypeList(appctnTypeList);

            pCBATechGuidanceInsertDTO.setSurveyInfoList(cBATechGuidanceMapper.selectTechGuidanceSurvey(cnstgSeq));

        }
        pCBATechGuidanceInsertDTO.setRsumeList(cBATechGuidanceMapper.selectTechGuidanceRsume(pCBATechGuidanceInsertDTO));

        return pCBATechGuidanceInsertDTO;
    }

    /**
     * 컨설팅 기술 지도 등록 관리자
     */
    public int insertTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {
        pCBATechGuidanceInsertDTO.setCnstgSeq(cosultSeqIdgen.getNextIntegerId());
        pCBATechGuidanceInsertDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo().replaceAll("-", ""));

        // 컨설팅 서브 정보 수정
        updateSubTechGuidanceInfo(pCBATechGuidanceInsertDTO);

        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBATechGuidanceInsertDTO.getFileList());
        pCBATechGuidanceInsertDTO.setItrdcFileSeq(fileSeqMap.get("itrdcFileSeq"));
        pCBATechGuidanceInsertDTO.setImpvmFileSeq(fileSeqMap.get("impvmFileSeq"));

        // 신청 회원 정보
        updateTechMemberInfo(pCBATechGuidanceInsertDTO);
        // 부품사 정보
        updateTechCompanyInfo(pCBATechGuidanceInsertDTO);


        pCBATechGuidanceInsertDTO.setRespCnt(cBATechGuidanceMapper.insertTechGuidance(pCBATechGuidanceInsertDTO));
        return pCBATechGuidanceInsertDTO.getRespCnt();
    }

    /**
     * 컨설팅 기술 지도 등록 사용자
     */
    public int insertUserTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, MultipartHttpServletRequest multiRequest) throws Exception {
        pCBATechGuidanceInsertDTO.setCnstgSeq(cosultSeqIdgen.getNextIntegerId());
        pCBATechGuidanceInsertDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo().replaceAll("-", ""));

        // 컨설팅 서브 정보 수정
        updateSubTechGuidanceInfo(pCBATechGuidanceInsertDTO);

        //신청파일 넣기
        List<COFileDTO> rtnList = null;
        Map<String, MultipartFile> files = multiRequest.getFileMap();
        Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        MultipartFile file;
        int atchFileCnt = 0;

        while (itr.hasNext()) {
            Map.Entry<String, MultipartFile> entry = itr.next();
            file = entry.getValue();

            if (file.getName().indexOf("atchFile") > -1 && file.getSize() > 0) {
                atchFileCnt++;
            }
        }

        if (!files.isEmpty()) {
            rtnList = cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 0);

            System.err.println(rtnList);
            for (int i = 0; i < rtnList.size(); i++) {

                List<COFileDTO> fileList = new ArrayList();
                rtnList.get(i).setStatus("success");
                rtnList.get(i).setFieldNm("fileSeq");
                fileList.add(rtnList.get(i));

                HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                if(i==0){
                    pCBATechGuidanceInsertDTO.setItrdcFileSeq(fileSeqMap.get("fileSeq"));
                }else{
                    pCBATechGuidanceInsertDTO.setImpvmFileSeq(fileSeqMap.get("fileSeq"));
                }
            }
        }
        cBATechGuidanceMapper.insertTechGuidance(pCBATechGuidanceInsertDTO);

        return pCBATechGuidanceInsertDTO.getCnstgSeq();
    }

    /**
     * 부품사 회원 정보 수정
     */
    void updateTechMemberInfo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        MPAUserDto mPAUserDto = new MPAUserDto();
        mPAUserDto.setDetailsKey(pCBATechGuidanceInsertDTO.getMemSeq());

        mPAUserDto = mpaUserService.selectUserDtlTab(mPAUserDto);

        if(mPAUserDto != null){
            mPAUserDto.setRegId(pCBATechGuidanceInsertDTO.getRegId());
            mPAUserDto.setRegIp(pCBATechGuidanceInsertDTO.getRegIp());
            mPAUserDto.setMemSeq(Integer.valueOf(pCBATechGuidanceInsertDTO.getMemSeq()));
            mPAUserDto.setDeptCd(pCBATechGuidanceInsertDTO.getDeptCd());
            mPAUserDto.setDeptDtlNm(pCBATechGuidanceInsertDTO.getDeptDtlNm());
            mPAUserDto.setPstnCd(pCBATechGuidanceInsertDTO.getPstnCd());
            mPAUserDto.setTelNo(pCBATechGuidanceInsertDTO.getTelNo());
            mPAUserDto.setHpNo(pCBATechGuidanceInsertDTO.getHpNo());
            mPAUserDto.setChngFndn("N");
            mpaUserService.updateUserDtl(mPAUserDto);
        }
    }

    /**
     * 부품사 정보 수정
     */
    void updateTechCompanyInfo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        MPEPartsCompanyDTO mPEPartsCompanyDTO = new MPEPartsCompanyDTO();
        mPEPartsCompanyDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo().replace("-", ""));
        mPEPartsCompanyDTO = mPEPartsCompanyService.selectPartsCompanyDtl(mPEPartsCompanyDTO);
        String ctgryCd = mPEPartsCompanyDTO.getList().get(0).getCtgryCd();

        // 1차사 - 5스타
        if(ctgryCd.equals("COMPANY01001")){
            mPEPartsCompanyDTO.setQlty5StarCd(pCBATechGuidanceInsertDTO.getQlty5StarCd());
            mPEPartsCompanyDTO.setQlty5StarYear(pCBATechGuidanceInsertDTO.getQlty5StarYear());
            mPEPartsCompanyDTO.setPay5StarCd(pCBATechGuidanceInsertDTO.getPay5StarCd());
            mPEPartsCompanyDTO.setPay5StarYear(pCBATechGuidanceInsertDTO.getPay5StarYear());
            mPEPartsCompanyDTO.setTchlg5StarCd(pCBATechGuidanceInsertDTO.getTchlg5StarCd());
            mPEPartsCompanyDTO.setTchlg5StarCd(pCBATechGuidanceInsertDTO.getTchlg5StarCd());
        } else if(ctgryCd.equals("COMPANY01002")){ // 2차사 - SQ 정보
            mpePartsCompanyMapper.deletePartsComSQInfo(mPEPartsCompanyDTO);
            for(int i=1; i < 4; i++) {
                mPEPartsCompanyDTO.setCbsnSeq(mpePartsCompanySqInfoDtlIdgen.getNextIntegerId());
                mPEPartsCompanyDTO.setRegId(pCBATechGuidanceInsertDTO.getRegId());
                mPEPartsCompanyDTO.setRegId(pCBATechGuidanceInsertDTO.getRegIp());
                if(i==1){
                    mPEPartsCompanyDTO.setNm(pCBATechGuidanceInsertDTO.getNm1());
                    mPEPartsCompanyDTO.setScore(pCBATechGuidanceInsertDTO.getScore1());
                    mPEPartsCompanyDTO.setYear(pCBATechGuidanceInsertDTO.getYear1());
                    mPEPartsCompanyDTO.setCrtfnCmpnNm(pCBATechGuidanceInsertDTO.getCrtfnCmpnNm1());
                    mPEPartsCompanyDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo());
                }else if(i==2){
                    mPEPartsCompanyDTO.setNm(pCBATechGuidanceInsertDTO.getNm2());
                    mPEPartsCompanyDTO.setScore(pCBATechGuidanceInsertDTO.getScore2());
                    mPEPartsCompanyDTO.setYear(pCBATechGuidanceInsertDTO.getYear2());
                    mPEPartsCompanyDTO.setCrtfnCmpnNm(pCBATechGuidanceInsertDTO.getCrtfnCmpnNm2());
                    mPEPartsCompanyDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo());
                }else{
                    mPEPartsCompanyDTO.setNm(pCBATechGuidanceInsertDTO.getNm3());
                    mPEPartsCompanyDTO.setScore(pCBATechGuidanceInsertDTO.getScore3());
                    mPEPartsCompanyDTO.setYear(pCBATechGuidanceInsertDTO.getYear3());
                    mPEPartsCompanyDTO.setCrtfnCmpnNm(pCBATechGuidanceInsertDTO.getCrtfnCmpnNm3());
                    mPEPartsCompanyDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo());
                }
                mpePartsCompanyMapper.insertPartsComSQInfo(mPEPartsCompanyDTO);
            }
        }
        mPEPartsCompanyDTO.setModId(pCBATechGuidanceInsertDTO.getRegId());
        mPEPartsCompanyDTO.setModIp(pCBATechGuidanceInsertDTO.getRegIp());
        mPEPartsCompanyDTO.setRprsntNm(pCBATechGuidanceInsertDTO.getRprsntNm());
        mPEPartsCompanyDTO.setCmpnNm(pCBATechGuidanceInsertDTO.getCmpnNm());
        mPEPartsCompanyDTO.setCmpnNfrmlNm(pCBATechGuidanceInsertDTO.getCmpnNfrmlNm());
        mPEPartsCompanyDTO.setCmpnCd(pCBATechGuidanceInsertDTO.getCmpnCd());
        mPEPartsCompanyDTO.setTelNo(pCBATechGuidanceInsertDTO.getCmpnTelNo()); // 부품사 일반 전화번호
        mPEPartsCompanyDTO.setCtgryCd(pCBATechGuidanceInsertDTO.getCtgryCd());
        mPEPartsCompanyDTO.setSizeCd(pCBATechGuidanceInsertDTO.getSizeCd());
        mPEPartsCompanyDTO.setStbsmDt(pCBATechGuidanceInsertDTO.getStbsmDt());
        mPEPartsCompanyDTO.setZipcode(pCBATechGuidanceInsertDTO.getZipcode());
        mPEPartsCompanyDTO.setBscAddr(pCBATechGuidanceInsertDTO.getBscAddr());
        mPEPartsCompanyDTO.setDtlAddr(pCBATechGuidanceInsertDTO.getDtlAddr());
        mPEPartsCompanyDTO.setSlsPmt(pCBATechGuidanceInsertDTO.getSlsPmt());
        mPEPartsCompanyDTO.setSlsYear(pCBATechGuidanceInsertDTO.getSlsYear());
        mPEPartsCompanyDTO.setMpleCnt(pCBATechGuidanceInsertDTO.getMpleCnt());
        mPEPartsCompanyDTO.setMjrPrdct1(pCBATechGuidanceInsertDTO.getMjrPrdct1());
        mPEPartsCompanyDTO.setMjrPrdct2(pCBATechGuidanceInsertDTO.getMjrPrdct2());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBATechGuidanceInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBATechGuidanceInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBATechGuidanceInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setRegId(pCBATechGuidanceInsertDTO.getRegId());
        mPEPartsCompanyDTO.setRegId(pCBATechGuidanceInsertDTO.getRegIp());

       mpePartsCompanyMapper.updatePartsCompany(mPEPartsCompanyDTO);
    }

    /**
     * 컨설팅 서브 정보 수정
     */
    void updateSubTechGuidanceInfo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        // 고객사 비율 상세 정보 등록, 있으면 수정
        String[] dlvryCmpnNm =  pCBATechGuidanceInsertDTO.getDlvryCmpnNm().split(",");
        String[] dlvryRate = pCBATechGuidanceInsertDTO.getDlvryRate().split(",");
        HashMap cnstgDlyvMap = new HashMap();

        List delValueList = new ArrayList();
        delValueList.add(pCBATechGuidanceInsertDTO.getCnstgSeq().toString());
        pCBATechGuidanceInsertDTO.setDelValueList(delValueList);
        cBATechGuidanceMapper.deleteConsultDlvryDtl(pCBATechGuidanceInsertDTO);

        for(int i=0; i < dlvryCmpnNm.length; i++) {
            cnstgDlyvMap.put("cnstgSeq", pCBATechGuidanceInsertDTO.getCnstgSeq());
            cnstgDlyvMap.put("cmpnDlvrySeq",dlvrySeqIdgen.getNextIntegerId());
            cnstgDlyvMap.put("dlvryCmpnNm", dlvryCmpnNm[i]);
            cnstgDlyvMap.put("dlvryRate", dlvryRate[i]);
            cnstgDlyvMap.put("regId", pCBATechGuidanceInsertDTO.getRegId());
            cnstgDlyvMap.put("regIp", pCBATechGuidanceInsertDTO.getRegIp());

            cBATechGuidanceMapper.insertCnstgDlvryInfo(cnstgDlyvMap);

        }
        // 완성차 의존율 상세 정보 등록 있으면 수정
        String[] dpbdnCmpnNm =  pCBATechGuidanceInsertDTO.getDpndnCmpnNm().split(",");
        String[] dpbdnRate = pCBATechGuidanceInsertDTO.getDpndnRate().split(",");

        cBATechGuidanceMapper.deleteConsultDpndnDtl(pCBATechGuidanceInsertDTO);
        HashMap dpndnMap = new HashMap();
        for(int i=0; i < dpbdnCmpnNm.length; i++){
            dpndnMap.put("cnstgSeq",pCBATechGuidanceInsertDTO.getCnstgSeq());
            dpndnMap.put("dpndnSeq",dpndnSeqIdgen.getNextIntegerId());
            dpndnMap.put("cmpnNm", dpbdnCmpnNm[i]);
            dpndnMap.put("dpndnRate", dpbdnRate[i]);
            dpndnMap.put("regId", pCBATechGuidanceInsertDTO.getRegId());
            dpndnMap.put("regIp", pCBATechGuidanceInsertDTO.getRegIp());

            cBATechGuidanceMapper.insertCnstgDpndnInfo(dpndnMap);
        }
        // 부품사 업종 상세 등록 있으면 수정
        HashMap cbsnCdMap = new HashMap();
        cbsnCdMap.put("cnstgSeq",pCBATechGuidanceInsertDTO.getCnstgSeq());
        cbsnCdMap.put("cbsnCd",  pCBATechGuidanceInsertDTO.getCbsnCd());
        cbsnCdMap.put("etcNm", pCBATechGuidanceInsertDTO.getEtcNm());
        cbsnCdMap.put("regIp", pCBATechGuidanceInsertDTO.getRegId());
        cbsnCdMap.put("regId", pCBATechGuidanceInsertDTO.getRegId());
        cbsnCdMap.put("regIp", pCBATechGuidanceInsertDTO.getRegIp());

        int cnt = cBATechGuidanceMapper.selectCnstgDpndnInfoCnt(cbsnCdMap);
        if(cnt > 0){
            cBATechGuidanceMapper.updateCbsnDtl(cbsnCdMap);
        }else{
            cBATechGuidanceMapper.insertCbsnDtl(cbsnCdMap);
        }

        // 신청 분야 상세
        String[] appctnTypeCd =  pCBATechGuidanceInsertDTO.getAppctnTypeCd().split(",");
        HashMap appctnTypeMap = new HashMap();
        appctnTypeMap.put("cnstgSeq",pCBATechGuidanceInsertDTO.getCnstgSeq());
        cBATechGuidanceMapper.deleteCnstgAppctnType(appctnTypeMap);

        for(int i=0; i < appctnTypeCd.length; i++){
            appctnTypeMap.put("cnstgSeq",pCBATechGuidanceInsertDTO.getCnstgSeq());
            appctnTypeMap.put("regIp", pCBATechGuidanceInsertDTO.getRegId());
            appctnTypeMap.put("regId", pCBATechGuidanceInsertDTO.getRegId());
            appctnTypeMap.put("regIp", pCBATechGuidanceInsertDTO.getRegIp());
            appctnTypeMap.put("appctnTypeCd", appctnTypeCd[i]);

            int cnstgSeq = pCBATechGuidanceInsertDTO.getCnstgSeq();

            cBATechGuidanceMapper.insertCnstgAppctnType(appctnTypeMap);
        }
    }

    /**
     * 컨설팅 기술 지도 수정
     */
    public int updateTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, CBATechGuidanceUpdateDTO pCBATechGuidanceupUpdateDTO ) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBATechGuidanceInsertDTO.getFileList());
        pCBATechGuidanceInsertDTO.setItrdcFileSeq(fileSeqMap.get("itrdcFileSeq"));
        pCBATechGuidanceInsertDTO.setImpvmFileSeq(fileSeqMap.get("impvmFileSeq"));

        pCBATechGuidanceupUpdateDTO.setInitVstFileSeq(fileSeqMap.get("initVstFileSeq")); // 초도방문자료
        pCBATechGuidanceupUpdateDTO.setKickfFileSeq(fileSeqMap.get("kickfFileSeq")); // 킥오프자료
        pCBATechGuidanceupUpdateDTO.setLvlupFileSeq(fileSeqMap.get("lvlupFileSeq")); // 랩업자료
        pCBATechGuidanceupUpdateDTO.setEtcFileSeq(fileSeqMap.get("etcFileSeq")); // 기타사업자료

        pCBATechGuidanceupUpdateDTO.setRegIp(pCBATechGuidanceInsertDTO.getRegIp());
        pCBATechGuidanceupUpdateDTO.setRegId(pCBATechGuidanceInsertDTO.getRegId());

        pCBATechGuidanceInsertDTO.setCnstgSeq(Integer.valueOf(pCBATechGuidanceInsertDTO.getDetailsKey()));

        pCBATechGuidanceInsertDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo().replaceAll("-", ""));

        // 부품사 회원 정보 수정
        updateTechMemberInfo(pCBATechGuidanceInsertDTO);
        // 부품사 정보 수정
        updateTechCompanyInfo(pCBATechGuidanceInsertDTO);
        // 신청 서브 정보 수정
        updateSubTechGuidanceInfo(pCBATechGuidanceInsertDTO);
        // 부품사 업종 상세 수정
        cBATechGuidanceMapper.updateCbsnDtl(pCBATechGuidanceInsertDTO);

        int cnstgSeq = pCBATechGuidanceInsertDTO.getCnstgSeq();

        int totCnt = cBATechGuidanceMapper.selectRsumeTotCnt(cnstgSeq);
        pCBATechGuidanceupUpdateDTO.setCnstgSeq(cnstgSeq);
        if(totCnt>0){
            pCBATechGuidanceupUpdateDTO.setModIp(pCBATechGuidanceInsertDTO.getRegIp());
            pCBATechGuidanceupUpdateDTO.setModId(pCBATechGuidanceInsertDTO.getRegId());
            cBATechGuidanceMapper.updateTechGuidanceRsume(pCBATechGuidanceupUpdateDTO);
        }else if(totCnt == 0){
            cBATechGuidanceMapper.insertTechGuidanceRsume(pCBATechGuidanceupUpdateDTO);
        }

        String rsumeSttsCd = pCBATechGuidanceInsertDTO.getRsumeSttsCd();

        // MNGTECH_STATUS_04  MNGTECH_STATUS_07 - 탈락 코드
        if(rsumeSttsCd.equals("MNGTECH_STATUS_04") || rsumeSttsCd.equals("MNGTECH_STATUS_07")){

            //이메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            //SMS 발송
            COSmsDTO smsDto = new COSmsDTO();
            cOMailDTO.setSubject("["+siteName+"] 컨설팅사업 탈락 안내");
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            CBATechGuidanceInsertDTO tempDto = new CBATechGuidanceInsertDTO();
            tempDto.setRegId(pCBATechGuidanceInsertDTO.getRegId());
            tempDto.setRegIp(pCBATechGuidanceInsertDTO.getRegIp());
            tempDto.setMemSeq(String.valueOf(pCBATechGuidanceInsertDTO.getMemSeq()));
            tempDto.setRsumeSttsCd("MNGTECH_STATUS01");
            tempDto.getDetailsKey();
            tempDto = cBATechGuidanceMapper.selectTechGuidanceDtl(pCBATechGuidanceInsertDTO);
            //탈락 사유(치환문자2)
            if(rsumeSttsCd.equals("MNGTECH_STATUS_04")){
                cOMailDTO.setEditorContents(pCBATechGuidanceupUpdateDTO.getBfreJdgmtRsltCntn());
                //receiverDto.setNote2(pCBATechGuidanceupUpdateDTO.getBfreJdgmtRsltCntn());
                //사업 진행 단계(치환문자4)
                receiverDto.setNote4("사전심사결과");
            }else if(rsumeSttsCd.equals("MNGTECH_STATUS_07")){
                cOMailDTO.setEditorContents(pCBATechGuidanceupUpdateDTO.getInitVstRsltCntn());
                //receiverDto.setNote2(pCBATechGuidanceupUpdateDTO.getInitVstRsltCntn());
                //사업 진행 단계(치환문자4)
                receiverDto.setNote4("초도방문결과");
            }
            //수신자 번호
            receiverDto.setMobile(tempDto.getHpNo());
            //이메일
            receiverDto.setEmail(tempDto.getEmail());
            //이름
            receiverDto.setName(tempDto.getName());
            //사업명(치환문자1)
            //receiverDto.setNote1("2024 상주기술지도");
            receiverDto.setNote1(tempDto.getBsnYear() + " 상주" +tempDto.getCnstgNm());
            //부품사명(치환문자3)
            receiverDto.setNote3(tempDto.getCmpnNm());
            //신청일
            String field2 = CODateUtil.convertDate(CODateUtil.getToday("yyyyMMddHHmm"),"yyyyMMddHHmm", "yyyy-MM-dd HH:mm", "");
            //수신자 정보 등록
            cOMailDTO.getReceiver().add(receiverDto);

            cOMessageService.sendMail(cOMailDTO, "CBTechGuidanceFailEmail.html");

            //문자 발송
            smsDto.setTitle("컨설팅사업 탈락 안내");
            smsDto.getReceiver().add(receiverDto);

            SMISmsCntnDTO smiSmsCntnDTO = new SMISmsCntnDTO();

            smiSmsCntnDTO.setSmsCntnCd("SMS08"); // 컨설팅사업 탈락 구분 코드
            smiSmsCntnDTO.setSmsCntnSeq(4);

            String cntn = "";
            if(rsumeSttsCd.equals("MNGTECH_STATUS_04")){
                SMISmsCntnDTO cntnDto = smiSmsCntnService.selectSmsCntnDtl(smiSmsCntnDTO);

                cntn = cntnDto.getCntn();
                cntn = cntn.replace("${editorContents}", pCBATechGuidanceupUpdateDTO.getBfreJdgmtRsltCntn());
                //사업 진행 단계(치환문자4)
                receiverDto.setNote4("사전심사결과");
            }else if(rsumeSttsCd.equals("MNGTECH_STATUS_07")){
                SMISmsCntnDTO cntnDto = smiSmsCntnService.selectSmsCntnDtl(smiSmsCntnDTO);

                cntn = cntnDto.getCntn();
                cntn = cntn.replace("${editorContents}", pCBATechGuidanceupUpdateDTO.getInitVstRsltCntn());
                //사업 진행 단계(치환문자4)
                receiverDto.setNote4("초도방문결과");
            }

            smsDto.setMessage(COStringUtil.replaceHTML(cntn));

            cOMessageService.sendSms(smsDto, "");
        }

        String bfreMemSeq = pCBATechGuidanceInsertDTO.getBfreMemSeq();
        String aftrMemSeq = pCBATechGuidanceInsertDTO.getMemSeq();
        if(!bfreMemSeq.equals(aftrMemSeq)){
            pCBATechGuidanceInsertDTO.setTrnsfSeq(consTrnsfSeqIdgen.getNextIntegerId());
            pCBATechGuidanceInsertDTO.setAftrMemSeq(aftrMemSeq);
            cBATechGuidanceMapper.insertTrsfGuidanceList(pCBATechGuidanceInsertDTO);
        }
        pCBATechGuidanceInsertDTO.setRespCnt(cBATechGuidanceMapper.updateTechGuidance(pCBATechGuidanceInsertDTO));

        return pCBATechGuidanceInsertDTO.getRespCnt();
    }

    /**
     * 부품 회사 정보 조회
     */
    public List<MPEPartsCompanyDTO> selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectPartsCompanyDtl(mpePartsCompanyDTO);
    }

    /**
     * 컨설팅 이관 내역 조회
     */
    public CBATechGuidanceInsertDTO selectTrsfGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception
    {

        List<CBATechGuidanceInsertDTO> trsfGuidanceList = new ArrayList();
        CBATechGuidanceInsertDTO trsfDto = new CBATechGuidanceInsertDTO();

        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(pCBATechGuidanceInsertDTO.getPageIndex());
        page.setRecordCountPerPage(pCBATechGuidanceInsertDTO.getListRowSize());

        page.setPageSize(pCBATechGuidanceInsertDTO.getPageRowSize());

        pCBATechGuidanceInsertDTO.setFirstIndex( page.getFirstRecordIndex() );
        pCBATechGuidanceInsertDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        trsfGuidanceList = cBATechGuidanceMapper.selectTrsfGuidanceList(pCBATechGuidanceInsertDTO);
        int trsfCnt = cBATechGuidanceMapper.selectTrsfGuidanceCnt(pCBATechGuidanceInsertDTO);

        trsfDto.setTrsfGuidanceList(trsfGuidanceList);
        trsfDto.setTotalCount(trsfCnt);

        return trsfDto;
    }

    /**
     * 만족도 종합 결과 리스트 조회
     */
    public CBAConsultSuveyRsltListDTO selectConsultSuveyRsltList(CBAConsultSuveyRsltListDTO pCBAConsultSuveyRsltListDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(pCBAConsultSuveyRsltListDTO.getPageIndex());
        page.setRecordCountPerPage(pCBAConsultSuveyRsltListDTO.getListRowSize());
        page.setPageSize(pCBAConsultSuveyRsltListDTO.getPageRowSize());
        pCBAConsultSuveyRsltListDTO.setFirstIndex(page.getFirstRecordIndex());
        pCBAConsultSuveyRsltListDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        pCBAConsultSuveyRsltListDTO.setTotalCount(cBATechGuidanceMapper.getConsultSuveyRsltCnt(pCBAConsultSuveyRsltListDTO));
        pCBAConsultSuveyRsltListDTO.setList(cBATechGuidanceMapper.selectConsultSuveyRsltList(pCBAConsultSuveyRsltListDTO));
        return pCBAConsultSuveyRsltListDTO;
    }

    /**
     * 만족도 종합 결과 엑셀다운로드 상세
     */
    public CBAConsultSuveyRsltListDTO selectConsultSuveyRsltDtlExcel(CBAConsultSuveyRsltListDTO pCBAConsultSuveyRsltListDTO) throws Exception{

        pCBAConsultSuveyRsltListDTO.setList(cBATechGuidanceMapper.selectConsultSuveyRsltDtlExcel(pCBAConsultSuveyRsltListDTO));
        return pCBAConsultSuveyRsltListDTO;
    }

    /**
     * 엑셀 생성
     */
    public void excelDownload(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, HttpServletResponse response) throws Exception{

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle style_header = workbook.createCellStyle();
        XSSFCellStyle style_body = workbook.createCellStyle();
        Sheet sheet = workbook.createSheet();

        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        //Cell Alignment 지정
        style_header.setAlignment(HorizontalAlignment.CENTER);
        style_header.setVerticalAlignment(VerticalAlignment.CENTER);
        style_body.setAlignment(HorizontalAlignment.CENTER);
        style_body.setVerticalAlignment(VerticalAlignment.CENTER);

        // Border Color 지정
        style_header.setBorderTop(BorderStyle.THIN);
        style_header.setBorderLeft(BorderStyle.THIN);
        style_header.setBorderRight(BorderStyle.THIN);
        style_header.setBorderBottom(BorderStyle.THIN);
        style_body.setBorderTop(BorderStyle.THIN);
        style_body.setBorderLeft(BorderStyle.THIN);
        style_body.setBorderRight(BorderStyle.THIN);
        style_body.setBorderBottom(BorderStyle.THIN);

        //BackGround Color 지정
        style_header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Header
        row = sheet.createRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue("번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(1);
        cell.setCellValue("사업연도");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("진행상태");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("매출액(억원)");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("직원수");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("신청업종");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("신청 소재지");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("SQ 인증 주관사");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("방문일");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("방문횟수");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("지도착수일");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("킥오프일");
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("렙업일");
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("신청일");
        cell.setCellStyle(style_header);

        // Body
        List<CBATechGuidanceInsertDTO> excelList = pCBATechGuidanceInsertDTO.getList();
        for (int i=0; i<excelList.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(pCBATechGuidanceInsertDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(excelList.get(i).getBsnYear());
            cell.setCellStyle(style_body);

            //진행상태
            cell = row.createCell(2);
            cell.setCellValue(excelList.get(i).getRsumeSttsNm());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(3);
            cell.setCellValue(excelList.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(4);
            cell.setCellValue(excelList.get(i).getAppctnBsnmNo());
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(5);
            cell.setCellValue(excelList.get(i).getCtgryNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(6);
            cell.setCellValue(excelList.get(i).getSizeNm());
            cell.setCellStyle(style_body);

            //매출액(억원)
            cell = row.createCell(7);
            cell.setCellValue(excelList.get(i).getSlsPmt() == null ? 0 : excelList.get(i).getSlsPmt());
            cell.setCellStyle(style_body);

            //직원수
            cell = row.createCell(8);
            cell.setCellValue(excelList.get(i).getMpleCnt() == null ? 0 : excelList.get(i).getMpleCnt());
            cell.setCellStyle(style_body);

            //신청업종
            cell = row.createCell(9);
            cell.setCellValue(excelList.get(i).getCbsnNm());
            cell.setCellStyle(style_body);

            //신청소재지
            cell = row.createCell(10);
            cell.setCellValue(excelList.get(i).getFirstRgnsNm() +" "+ excelList.get(i).getScndRgnsNm());
            cell.setCellStyle(style_body);

            //SQ 인증 주관사
            cell = row.createCell(11);
            cell.setCellValue(excelList.get(i).getCrtfnCmpnNm());
            cell.setCellStyle(style_body);

            //방문일
            cell = row.createCell(12);
            cell.setCellValue(excelList.get(i).getVstDt() == null ? "-" : excelList.get(i).getVstDt());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(13);
            cell.setCellValue(excelList.get(i).getCmssrNm() == null ? "-" : excelList.get(i).getCmssrNm());
            cell.setCellStyle(style_body);

            //방문횟수
            cell = row.createCell(14);
            cell.setCellValue(excelList.get(i).getVstCnt() == null ? "-" : excelList.get(i).getVstCnt());
            cell.setCellStyle(style_body);

            //지도착수일
            cell = row.createCell(15);
            cell.setCellValue(excelList.get(i).getGuideBgnDt() == null ? "-" : excelList.get(i).getGuideBgnDt());
            cell.setCellStyle(style_body);

            //킥오프일
            cell = row.createCell(16);
            cell.setCellValue(excelList.get(i).getGuideKickfDt() == null ? "-" : excelList.get(i).getGuideKickfDt());
            cell.setCellStyle(style_body);

            //렙업일
            cell = row.createCell(17);
            cell.setCellValue(excelList.get(i).getGuidePscndDt() == null ? "-" : excelList.get(i).getGuidePscndDt());
            cell.setCellStyle(style_body);

            //신청일
            cell = row.createCell(18);
            cell.setCellValue(excelList.get(i).getAppctnDt());
            cell.setCellStyle(style_body);
        }

        // 열 너비 설정
       /* for(int i =0; i < 8; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i)  + 800));
        }*/

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("기술지도_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("컨설팅사업관리 > 기술지도");
        pCoSystemLogDTO.setSrvcNm("mngwserc.cb.cba.service.impl.CBATechGuidanceServiceImpl");
        pCoSystemLogDTO.setFncNm("selectManageConsultList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(pCBATechGuidanceInsertDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 만족도 종합 결과 상세 엑셀 생성
     */
    public void srvRsltExcelDownload(CBAConsultSuveyRsltListDTO pCBAConsultSuveyRsltListDTO, HttpServletResponse response) throws Exception{

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle style_header = workbook.createCellStyle();
        XSSFCellStyle style_body = workbook.createCellStyle();
        Sheet sheet = workbook.createSheet();

        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        //Cell Alignment 지정
        style_header.setAlignment(HorizontalAlignment.CENTER);
        style_header.setVerticalAlignment(VerticalAlignment.CENTER);
        style_body.setAlignment(HorizontalAlignment.CENTER);
        style_body.setVerticalAlignment(VerticalAlignment.CENTER);

        // Border Color 지정
        style_header.setBorderTop(BorderStyle.THIN);
        style_header.setBorderLeft(BorderStyle.THIN);
        style_header.setBorderRight(BorderStyle.THIN);
        style_header.setBorderBottom(BorderStyle.THIN);
        style_body.setBorderTop(BorderStyle.THIN);
        style_body.setBorderLeft(BorderStyle.THIN);
        style_body.setBorderRight(BorderStyle.THIN);
        style_body.setBorderBottom(BorderStyle.THIN);

        //BackGround Color 지정
        style_header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Header
        row = sheet.createRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue("번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(1);
        cell.setCellValue("사업연도");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("참여자");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("신청업종/분야");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("지도구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("상태");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("렙업일");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("총점(100)");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("지도실적(50)");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("의사소동(5)");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("기획력(10)");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("실행력(15)");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("마인드(5)");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("전문지식(15)");
        cell.setCellStyle(style_header);


        // Body
        List<CBAConsultSuveyRsltListDTO> list = pCBAConsultSuveyRsltListDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(rowNum-1);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getBsnYear());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getCmssrNm() == null ? "-" : list.get(i).getCmssrNm());
            cell.setCellStyle(style_body);

            //참여자
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getPtcptName() == null ? "-" : list.get(i).getPtcptName());
            cell.setCellStyle(style_body);

            //신청업종/분야
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getAppctnFldNm());
            cell.setCellStyle(style_body);

            //지도구분
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getGuideTypeNm());
            cell.setCellStyle(style_body);

            //상태
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getSrvStts());
            cell.setCellStyle(style_body);

            //렙업일
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getLvlupDt() == null ? "-" : list.get(i).getLvlupDt());
            cell.setCellStyle(style_body);

            //총점(100)
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getTtlScore());
            cell.setCellStyle(style_body);

            //지도실적(50)
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getGuideRsltScore());
            cell.setCellStyle(style_body);

            //의사소통(5)
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getCmmnctnScore());
            cell.setCellStyle(style_body);

            //기획력(10)
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getPlnngabltScore());
            cell.setCellStyle(style_body);

            //실행력(15)
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getExctvabltScore());
            cell.setCellStyle(style_body);

            //마인드(5)
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getMndScore());
            cell.setCellStyle(style_body);

            //전문지식(15)
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getExprtsScore());
            cell.setCellStyle(style_body);

        }


        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("기술지도_만족도종합결과_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("컨설팅사업관리 > 기술지도");
        pCoSystemLogDTO.setSrvcNm("mngwserc.cb.cba.service.impl.CBATechGuidanceServiceImpl");
        pCoSystemLogDTO.setFncNm("selectConsultSuveyRsltDtlExcel");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(pCBAConsultSuveyRsltListDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 컨설팅 기술 지도 삭제
     */
    @Transactional
    public int deleteTechGuidance(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO) throws Exception
    {
        int actCnt = 0;

        // 참여이관로그 테이블 삭제
        cBATechGuidanceMapper.deleteConsultAppctnTrnsfDtl(cBATechGuidanceInsertDTO);
        // 만족도 결과 상세 테이블 삭제
        cBATechGuidanceMapper.deleteConsultSrvRsltDtl(cBATechGuidanceInsertDTO);
        // 신청분야 상세 테이블 삭제 (경영컨설팅에서는 안씀)
        cBATechGuidanceMapper.deleteConsultAppctnTypeDtl(cBATechGuidanceInsertDTO);
        // 완성차의존율 상세 테이블 삭제
        cBATechGuidanceMapper.deleteConsultDpndnDtl(cBATechGuidanceInsertDTO);
        // 고객사비율 상세 테이블 삭제
        cBATechGuidanceMapper.deleteConsultDlvryDtl(cBATechGuidanceInsertDTO);
        // 부품사 업종 상세 테이블 삭제 (경영컨설팅에서는 안씀)
        cBATechGuidanceMapper.deleteConsultCbsnDtl(cBATechGuidanceInsertDTO);
        // 담당임원 테이블 삭제
        cBATechGuidanceMapper.deleteConsultPicDtl(cBATechGuidanceInsertDTO);
        // 진행마스터 테이블 삭제
        cBATechGuidanceMapper.deleteConsultRsumeMst(cBATechGuidanceInsertDTO);

        actCnt = cBATechGuidanceMapper.deleteManageConsult(cBATechGuidanceInsertDTO);

        return actCnt;
    }


    /**
     * 컨설팅 기술 지도 상세 조회
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceDtlCheck(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        if (!"".equals(pCBATechGuidanceInsertDTO.getDetailsKey()))
        {
            pCBATechGuidanceInsertDTO = cBATechGuidanceMapper.selectTechGuidanceDtlCheck(pCBATechGuidanceInsertDTO);
        }

        return pCBATechGuidanceInsertDTO;
    }

    /**
     * 최근 1년 간 신청한 컨설팅 카운트(마이페이지)
     */
    public int countConsultingApplication(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        int actCnt = 0;
        if (!"".equals(pCBATechGuidanceInsertDTO.getMemSeq())) {
            actCnt = cBATechGuidanceMapper.selectYearConsultingCount(pCBATechGuidanceInsertDTO);
        }
        return actCnt;
    }


    /**
     * 최근 1년 간 신청한 컨설팅 카운트(사용자취소 제외)
     */
    public int selectYearCancelNotConsultingCount(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        int actCnt = 0;
        if (!"".equals(pCBATechGuidanceInsertDTO.getMemSeq())) {
            actCnt = cBATechGuidanceMapper.selectYearCancelNotConsultingCount(pCBATechGuidanceInsertDTO);
        }
        return actCnt;
    }

    /**
     * 최근 1년 간 신청한 컨설팅 카운트(마이페이지)
     */
    public CBATechGuidanceInsertDTO selectCompleteInfo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        return cBATechGuidanceMapper.selectCompleteInfo(pCBATechGuidanceInsertDTO);
    }

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    @Transactional
    public int updAdmMemo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        int respCnt = 0;

        respCnt = cBATechGuidanceMapper.updAdmMemo(pCBATechGuidanceInsertDTO);

        return respCnt;
    }


    /**
     * 등록된 설문 초기화 (삭제)
     */
    @Transactional
    public int deleteSurveyRspn(CBATechGuidanceUpdateDTO pCBATechGuidanceUpdateDTO) throws Exception
    {
        int rtnCnt = cBATechGuidanceMapper.deleteSurveyRspn(pCBATechGuidanceUpdateDTO);

        if(rtnCnt > 0) {
            CBATechGuidanceInsertDTO temp = new CBATechGuidanceInsertDTO();
            List<String> tmp = new ArrayList<>();
            tmp.add(pCBATechGuidanceUpdateDTO.getCnstgSeq().toString());
            temp.setDelValueList(tmp);

            cBATechGuidanceMapper.deleteConsultSrvRsltDtl(temp);
        }

        return rtnCnt;
    }

    /**
     * 등록된 설문 개수 조회
     */
    @Transactional
    public int checkSurveyCnt(CBATechGuidanceUpdateDTO pCBATechGuidanceUpdateDTO) throws Exception
    {
        return cBATechGuidanceMapper.checkSurveyCnt(pCBATechGuidanceUpdateDTO);
    }

    /**
     * 등록된 설문 개수 조회
     */
    public int selectApplyCnmstgSeq(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception
    {
        return cBATechGuidanceMapper.selectApplyCnmstgSeq(pCBATechGuidanceInsertDTO);
    }



}

