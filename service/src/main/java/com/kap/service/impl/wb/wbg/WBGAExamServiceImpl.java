package com.kap.service.impl.wb.wbg;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbg.*;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBGAExamService;
import com.kap.service.dao.wb.wbg.WBGAExamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 신청업체관리 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBGAExamServiceImpl.java
 * @Description		: 신청업체관리 ServiceImpl
 * @author 김대성
 * @since 2023.12.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김대성				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WBGAExamServiceImpl implements WBGAExamService {
    //Mapper
    private final WBGAExamMapper wBGAExamMapper;
    private final COFileService cOFileService;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;
    //상생신청 마스터 시퀀스
    private final EgovIdGnrService cxAppctnMstSeqIdgen;
    //대상장비 상세 시퀀스
    private final EgovIdGnrService cxAppctnTchlgSeqIdgen;
    //부품사관리 시퀀스
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;
    //상생신청 상세 시퀀스
    private final EgovIdGnrService cxAppctnRsumeDtlSeqIdgen;
    //참여이관 시퀀스
    private final EgovIdGnrService cxAppctnTrnsfDtlIdgen;
    /* 사업신청자격 시퀀스 */
    private final EgovIdGnrService cxAppctnValidMstIdgen;

    @Override
    public WBGAConsultingDTO getConsultingList(WBGAConsultingDTO wBGAConsultingDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBGAConsultingDTO.getPageIndex());
        page.setRecordCountPerPage(wBGAConsultingDTO.getListRowSize());

        page.setPageSize(wBGAConsultingDTO.getPageRowSize());

        wBGAConsultingDTO.setFirstIndex(page.getFirstRecordIndex());
        wBGAConsultingDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBGAConsultingDTO.setList(wBGAExamMapper.getConsultingList(wBGAConsultingDTO));
        wBGAConsultingDTO.setTotalCount(wBGAExamMapper.getConsultingCount(wBGAConsultingDTO));

        return wBGAConsultingDTO;
    }

    /**
     * 신청 목록
     */
    public WBGAExamSearchDTO selectCalibrationList(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBGAExamSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBGAExamSearchDTO.getListRowSize());

        page.setPageSize(wBGAExamSearchDTO.getPageRowSize());

        wBGAExamSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBGAExamSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBGAExamSearchDTO.setList(wBGAExamMapper.selectCalibrationList(wBGAExamSearchDTO));
        wBGAExamSearchDTO.setTotalCount(wBGAExamMapper.getCalibrationListTotCnt(wBGAExamSearchDTO));

        return wBGAExamSearchDTO;
    }

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {
        return wBGAExamMapper.selectYearDtl(wBGAExamSearchDTO);
    }


    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {


        List<String> appctnSeqList = wBGAExamSearchDTO.getDelValueList();

        wBGAExamSearchDTO.setDelValueList(appctnSeqList);

        //이관삭제
        wBGAExamMapper.carbonCompanyDeleteTrnsf(wBGAExamSearchDTO);
        //장비삭제
        wBGAExamMapper.carbonCompanyDeleteTchlg(wBGAExamSearchDTO);


        wBGAExamSearchDTO.setDelValueList(wBGAExamMapper.selectRsumeSeq(wBGAExamSearchDTO));

        //신청진행계측 삭제
        wBGAExamMapper.carbonCompanyDeleteRsumeTchlg(wBGAExamSearchDTO);
        //신청파일 삭제
        wBGAExamMapper.carbonCompanyDeleteRsumeFile(wBGAExamSearchDTO);


        wBGAExamSearchDTO.setDelValueList(appctnSeqList);
        //신청진행상세 삭제
        wBGAExamMapper.carbonCompanyDeleteRsume(wBGAExamSearchDTO);

        int respCnt = wBGAExamMapper.carbonCompanyDeleteMst(wBGAExamSearchDTO);

        return respCnt;

    }

    /**
     * 옵션 목록
     */
    public WBGAValidDTO selectExamValid(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {
        WBGAValidDTO wBGAValidDTO = new WBGAValidDTO();
        wBGAValidDTO = wBGAExamMapper.selectExamValid(wBGAExamSearchDTO);

        List<WBGAValidDtlDTO>  wBGAValidDtlDTO = wBGAExamMapper.selectExamValidDtlList(wBGAExamSearchDTO);
        if(wBGAValidDtlDTO.size() > 0){
            wBGAValidDTO.setDtlList(wBGAValidDtlDTO);
        }

        return wBGAValidDTO;
    }

    /**
     * 옵션 수정
     */
    public int examValidUpdate(WBGAValidDTO wBGAValidDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int appctnValidSeqIdgen;

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();


        if(wBGAValidDTO.getValidSeq() != null && !"".equals(wBGAValidDTO.getValidSeq())){
            appctnValidSeqIdgen = wBGAValidDTO.getValidSeq();
            wBGAValidDTO.setModId(coaAdmDTO.getId());
            wBGAValidDTO.setModIp(coaAdmDTO.getLoginIp());
            respCnt = wBGAExamMapper.examValidUpdate(wBGAValidDTO);
        }else{
            appctnValidSeqIdgen = cxAppctnValidMstIdgen.getNextIntegerId();
            wBGAValidDTO.setValidSeq(appctnValidSeqIdgen);
            wBGAValidDTO.setRegId(coaAdmDTO.getId());
            wBGAValidDTO.setRegIp(coaAdmDTO.getLoginIp());
            respCnt = wBGAExamMapper.examValidInsert(wBGAValidDTO);
        }


        wBGAExamMapper.deleteValidDtl(wBGAValidDTO);

        for(int i=0; i < wBGAValidDTO.getDtlList().size(); i++){
            WBGAValidDtlDTO wBGAValidDtlDTO = wBGAValidDTO.getDtlList().get(i);
            wBGAValidDtlDTO.setValidSeq(appctnValidSeqIdgen);
            wBGAValidDtlDTO.setRegId(coaAdmDTO.getId());
            wBGAValidDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBGAExamMapper.examValidDtlInsert(wBGAValidDtlDTO);
        }

        return respCnt;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBGAExamSearchDTO wBGAExamSearchDTO, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("관리자상태값");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("신청자(아이디)");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("휴대폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("전년도 매출액");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("장비내역");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("수량");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("투자금액");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("재단지원금");
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("실지급일");
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("관리자등록일");
        cell.setCellStyle(style_header);

        cell = row.createCell(19);
        cell.setCellValue("사용자수정일");
        cell.setCellStyle(style_header);


        // Body
        List<WBGAExamSearchDTO> list = wBGAExamSearchDTO.getList();

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBGAExamSearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getYear());
            cell.setCellStyle(style_body);

            //진행상태
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getRsumeSttsNm());
            cell.setCellStyle(style_body);

            //관리자 상태값
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getMngSttsNm());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getBsnmNo());
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getCtgryNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getSizeNm());
            cell.setCellStyle(style_body);

            //신청자
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getName() +"("+ list.get(i).getId() +")");
            cell.setCellStyle(style_body);

            //휴대폰
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //매출액
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getSlsPmt());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getPicCmssrNm());
            cell.setCellStyle(style_body);

            //장비내역
            cell = row.createCell(13);
            if(list.get(i).getTchlgOrdMax() > 1){
                cell.setCellValue(list.get(i).getTchlgNm()+ " 등 " + (list.get(i).getTchlgOrdMax() -1));
            }else{
                cell.setCellValue(list.get(i).getTchlgNm());
            }
            cell.setCellStyle(style_body);

            //수량
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getTchlgCntSum());
            cell.setCellStyle(style_body);

            //투자금액
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getNvstmPmt());
            cell.setCellStyle(style_body);

            //재단지원금
            cell = row.createCell(16);
            cell.setCellValue(list.get(i).getFndnSpprtPmt());
            cell.setCellStyle(style_body);

            //실지급일
            cell = row.createCell(17);
            cell.setCellValue(list.get(i).getRealGiveDt());
            cell.setCellStyle(style_body);

            //관리자 등록일
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getRegDtm());
            cell.setCellStyle(style_body);

            //사용자 수정일
            cell = row.createCell(19);
            cell.setCellValue(list.get(i).getModDtm());
            cell.setCellStyle(style_body);
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_신청부품사관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     * 진행상태 목록을 조회한다.
     * @return
     */
    public WBGAApplyMstDTO selectCompanyDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {

        WBGAApplyMstDTO wBGAApplyMstDTO = wBGAExamMapper.selectApplyMst(wBGAExamSearchDTO);

        List<WBGAApplyDtlDTO> selectApplyDtlList = wBGAExamMapper.selectApplyDtlList(wBGAExamSearchDTO);
        List<WBGAEuipmentDTO> selectEuipemtDtlList = wBGAExamMapper.selectEuipemtDtlList(wBGAExamSearchDTO);

        wBGAApplyMstDTO.setEuipmentList(selectEuipemtDtlList);

        wBGAApplyMstDTO.setApplyList(new ArrayList<WBGAApplyDtlDTO>());

        for (WBGAApplyDtlDTO dtlDto : selectApplyDtlList) {

            List<WBGAApplyDtlDTO> fileList = wBGAExamMapper.selectFileList(dtlDto);
            List<WBGAMsEuipmentDTO> msEuipmentList = wBGAExamMapper.selectMsEuipmentList(dtlDto);

            dtlDto.setFileInfoList(new ArrayList<WBGAApplyDtlDTO>());
            dtlDto.setMsEquipmentList(new ArrayList<WBGAMsEuipmentDTO>());

            //단계별 파일정보 넣기
            for(WBGAApplyDtlDTO fileDTO : fileList) {
                if (fileDTO.getRsumeSeq().equals(dtlDto.getRsumeSeq()) && fileDTO.getRsumeOrd().equals(dtlDto.getRsumeOrd())) {
                    dtlDto.getFileInfoList().add(fileDTO);
                }
            }

            //단계별 계측장비정보 넣기
            for(WBGAMsEuipmentDTO msEuipmentDTO : msEuipmentList) {
                if (msEuipmentDTO.getRsumeSeq().equals(dtlDto.getRsumeSeq()) && msEuipmentDTO.getRsumeOrd().equals(dtlDto.getRsumeOrd())) {
                    dtlDto.getMsEquipmentList().add(msEuipmentDTO);
                }
            }

            wBGAApplyMstDTO.getApplyList().add(dtlDto);
        }

        wBGAApplyMstDTO.setRsumeSttsNm(selectApplyDtlList.get(selectApplyDtlList.size()-1).getRsumeSttsNm());
        wBGAApplyMstDTO.setMngSttsNm(selectApplyDtlList.get(selectApplyDtlList.size()-1).getMngSttsNm());
        wBGAApplyMstDTO.setStageOrd(selectApplyDtlList.get(selectApplyDtlList.size()-1).getRsumeOrd());

        return wBGAApplyMstDTO;
    }

    /**
     * 부품사 회원 정보를 조회한다.
     * @return
     */
    public WBGACompanyDTO selectCompanyUserDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {

        WBGACompanyDTO wBGACompanyDTO = new WBGACompanyDTO();

        wBGACompanyDTO = wBGAExamMapper.getCompanyInfo(wBGAExamSearchDTO);
        List<WBGACompanyDTO> sqList = wBGAExamMapper.selectSqList(wBGAExamSearchDTO);
        wBGACompanyDTO.setSqInfoList(sqList);
        wBGACompanyDTO.setMemSeq(wBGAExamSearchDTO.getMemSeq());

        return wBGACompanyDTO;
    }

    /**
     * 부품사 신청자를 등록한다.
     */
    @Transactional
    public int insert(WBGACompanyDTO wBGACompanyDTO, WBGAApplyMstDTO wBGAApplyMstDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {

            //신청시간 년도 구하기
            Date now = new Date();
            SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");

            wBGAApplyMstDTO.setYear(yyyy.format(now));

            WBRoundMstSearchDTO round = wBGAExamMapper.getExisdDtl(wBGAApplyMstDTO);

            String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String regIp = CONetworkUtil.getMyIPaddress(request);

            if (round == null) {
                //해당년도 회차 미 존재시 신규회차 생성
                int exipsdSeq = cxEpisdSeqIdgen.getNextIntegerId();

                WBRoundMstSearchDTO roundDtl = new WBRoundMstSearchDTO();

                roundDtl.setEpisdSeq(exipsdSeq);
                roundDtl.setBsnCd(wBGAApplyMstDTO.getBsnCd());
                roundDtl.setYear(Integer.valueOf(wBGAApplyMstDTO.getYear()));
                roundDtl.setEpisd(1); //회차는 1회차 고정
                roundDtl.setAccsStrtDtm(wBGAApplyMstDTO.getYear()+"-01-01 00:00:00");
                roundDtl.setAccsEndDtm(wBGAApplyMstDTO.getYear()+"-12-31 23:59:59");
                roundDtl.setBsnStrtDtm(wBGAApplyMstDTO.getYear()+"-01-01 00:00:00");
                roundDtl.setBsnEndDtm(wBGAApplyMstDTO.getYear()+"-12-31 23:59:59");
                roundDtl.setExpsYn("Y");
                roundDtl.setRegId(regId);
                roundDtl.setRegIp(regIp);

                wBGAExamMapper.insetRound(roundDtl);

                wBGAApplyMstDTO.setEpisdSeq(exipsdSeq);
            } else {
                wBGAApplyMstDTO.setEpisdSeq(round.getEpisdSeq());
            }

            int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
            //마스터 생성
            wBGAApplyMstDTO.setAppctnSeq(appctnSeq);
            wBGAApplyMstDTO.setAppctnBsnmNo(wBGACompanyDTO.getBsnmNo());
            wBGAApplyMstDTO.setRegId(regId);
            wBGAApplyMstDTO.setRegIp(regIp);

            rtnCnt = wBGAExamMapper.insertApply(wBGAApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상세 생성
                WBGAApplyDtlDTO wBGAApplyDtlDTO = new WBGAApplyDtlDTO();
                wBGAApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBGAApplyDtlDTO.setAppctnSeq(wBGAApplyMstDTO.getAppctnSeq());
                wBGAApplyDtlDTO.setRsumeOrd(1);
                wBGAApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07001");
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_001");
                wBGAApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_02_001");
                wBGAApplyDtlDTO.setRegId(regId);
                wBGAApplyDtlDTO.setRegIp(regIp);

                wBGAExamMapper.insertApplyDtl(wBGAApplyDtlDTO);


                //신청대상장비 생성
                for(int j=0; j<wBGAApplyMstDTO.getEuipmentList().size(); j++) {
                    WBGAEuipmentDTO wBGAEuipmentDTO = new WBGAEuipmentDTO();
                    int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                    wBGAEuipmentDTO.setAppctnSeq(appctnSeq);
                    wBGAEuipmentDTO.setTchlgSeq(tchlgSeq);
                    wBGAEuipmentDTO.setTchlgOrd(j+1);
                    wBGAEuipmentDTO.setTchlgNm(wBGAApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                    wBGAEuipmentDTO.setTchlgCnt(wBGAApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                    wBGAEuipmentDTO.setRegId(regId);
                    wBGAEuipmentDTO.setRegIp(regIp);

                    wBGAExamMapper.insertEuipment(wBGAEuipmentDTO);
                }

                //신청파일 넣기
                for (int i = 0; i < wBGAApplyMstDTO.getFileList().size(); i++) {

                    List<COFileDTO> fileList = new ArrayList();
                    fileList.add(wBGAApplyMstDTO.getFileList().get(i));

                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                    wBGAApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    wBGAApplyDtlDTO.setFileCd(wBGAApplyMstDTO.getFileCdList().get(i));

                    wBGAExamMapper.insertFileInfo(wBGAApplyDtlDTO);
                }

                // 부품사 정보수정 Star
                wBGACompanyDTO.setRegId(regId);
                wBGACompanyDTO.setRegIp(regIp);
                wBGACompanyDTO.setModId(regId);
                wBGACompanyDTO.setModIp(regIp);

                //부품사 회원정보 수정
                wBGAExamMapper.updatePartUser(wBGACompanyDTO);

                String seq = "";

                if (wBGACompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for (int t = 0; t < wBGACompanyDTO.getSqInfoList().size(); t++) {
                        seq = String.valueOf(wBGACompanyDTO.getSqInfoList().get(t).getCbsnSeq());

                        wBGACompanyDTO.setYear(wBGACompanyDTO.getSqInfoList().get(t).getYear());
                        wBGACompanyDTO.setScore(wBGACompanyDTO.getSqInfoList().get(t).getScore());
                        wBGACompanyDTO.setNm(wBGACompanyDTO.getSqInfoList().get(t).getNm());
                        wBGACompanyDTO.setCrtfnCmpnNm(wBGACompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wBGACompanyDTO.setTchlg5StarCd(null);
                        wBGACompanyDTO.setPay5StarCd(null);
                        wBGACompanyDTO.setQlty5StarCd(null);
                        wBGACompanyDTO.setTchlg5StarYear(null);
                        wBGACompanyDTO.setPay5StarYear(null);
                        wBGACompanyDTO.setQlty5StarYear(null);

                        if (!seq.isEmpty()) {
                            wBGACompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wBGAExamMapper.updatePartsComSQInfo(wBGACompanyDTO);
                        } else {
                            wBGACompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wBGAExamMapper.insertPartsComSQInfo(wBGACompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wBGACompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wBGAExamMapper.deletePartsComSQInfo(wBGACompanyDTO);
                }

                wBGAExamMapper.updatePartsCompany(wBGACompanyDTO);
                // 부품사 수정 End
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 부품사 신청자를 수정한다.
     */
    @Transactional
    public int update(WBGACompanyDTO wBGACompanyDTO, WBGAApplyMstDTO wBGAApplyMstDTO, WBGAMsEuipmentDTO wBGAMsEuipmentDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            wBGAApplyMstDTO.setAppctnBsnmNo(wBGACompanyDTO.getBsnmNo());
            wBGAApplyMstDTO.setModId(modId);
            wBGAApplyMstDTO.setModIp(modIp);

            rtnCnt = wBGAExamMapper.updateApply(wBGAApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상태 업데이트
                //관리자상태에 따라 분기처리해야함
                WBGAApplyDtlDTO wBGAApplyDtlDTO = new WBGAApplyDtlDTO();
                wBGAApplyDtlDTO.setRsumeSeq(wBGAApplyMstDTO.getRsumeSeq());
                wBGAApplyDtlDTO.setRsumeOrd(wBGAApplyMstDTO.getRsumeOrd());
                wBGAApplyDtlDTO.setAppctnSeq(Integer.valueOf(wBGAApplyMstDTO.getDetailsKey()));
                wBGAApplyDtlDTO.setMngSttsCd(wBGAApplyMstDTO.getMngSttsCd());
                wBGAApplyDtlDTO.setRegId(modId);
                wBGAApplyDtlDTO.setRegIp(modIp);
                wBGAApplyDtlDTO.setModId(modId);
                wBGAApplyDtlDTO.setModIp(modIp);


                //신청대상장비 생성시작
                wBGAExamMapper.deleteEuipment(wBGAApplyDtlDTO);

                for(int j=0; j<wBGAApplyMstDTO.getEuipmentList().size(); j++) {
                    WBGAEuipmentDTO wBGAEuipmentDTO = new WBGAEuipmentDTO();
                    int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                    wBGAEuipmentDTO.setAppctnSeq(wBGAApplyDtlDTO.getAppctnSeq());
                    wBGAEuipmentDTO.setTchlgSeq(tchlgSeq);
                    wBGAEuipmentDTO.setTchlgOrd(j+1);
                    wBGAEuipmentDTO.setTchlgNm(wBGAApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                    wBGAEuipmentDTO.setTchlgCnt(wBGAApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                    wBGAEuipmentDTO.setRegId(modId);
                    wBGAEuipmentDTO.setRegIp(modIp);

                    wBGAExamMapper.insertEuipment(wBGAEuipmentDTO);
                }

                //단계별 프로세스
                wBGAApplyDtlDTO = stepUpdateProcess(wBGAApplyMstDTO,wBGAApplyDtlDTO, wBGAMsEuipmentDTO);

                //신청자 변경
                if ("Y".equals(wBGAApplyMstDTO.getUserLogYn())) {
                    WBBATransDTO wbbTransDTO = new WBBATransDTO();

                    wbbTransDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
                    wbbTransDTO.setAppctnSeq(Integer.valueOf(wBGAApplyMstDTO.getDetailsKey()));
                    wbbTransDTO.setBfreMemSeq(wBGAApplyMstDTO.getWbbTransDTO().getBfreMemSeq());
                    wbbTransDTO.setAftrMemSeq(wBGAApplyMstDTO.getWbbTransDTO().getAftrMemSeq());
                    wbbTransDTO.setRegId(modId);
                    wbbTransDTO.setRegIp(modIp);

                    //상생참여이관로그 생성
                    wBGAExamMapper.insertTransUserLog(wbbTransDTO);
                }


                //부품사 회원정보 수정 START
                wBGACompanyDTO.setRegId(modId);
                wBGACompanyDTO.setRegIp(modIp);
                wBGACompanyDTO.setModId(modId);
                wBGACompanyDTO.setModIp(modIp);
                wBGAExamMapper.updatePartUser(wBGACompanyDTO);

                // 부품사 정보수정 Start
                String seq = "";


                if (wBGACompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for(int t=0; t<wBGACompanyDTO.getSqInfoList().size();t++) {
                        seq = String.valueOf(wBGACompanyDTO.getSqInfoList().get(t).getCbsnSeq());


                        wBGACompanyDTO.setYear(wBGACompanyDTO.getSqInfoList().get(t).getYear());
                        wBGACompanyDTO.setScore(wBGACompanyDTO.getSqInfoList().get(t).getScore());
                        wBGACompanyDTO.setNm(wBGACompanyDTO.getSqInfoList().get(t).getNm());
                        wBGACompanyDTO.setCrtfnCmpnNm(wBGACompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wBGACompanyDTO.setTchlg5StarCd(null);
                        wBGACompanyDTO.setPay5StarCd(null);
                        wBGACompanyDTO.setQlty5StarCd(null);
                        wBGACompanyDTO.setTchlg5StarYear(null);
                        wBGACompanyDTO.setPay5StarYear(null);
                        wBGACompanyDTO.setQlty5StarYear(null);

                        if (!seq.isEmpty()) {
                            wBGACompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wBGAExamMapper.updatePartsComSQInfo(wBGACompanyDTO);
                        } else {
                            wBGACompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wBGAExamMapper.insertPartsComSQInfo(wBGACompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wBGACompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wBGAExamMapper.deletePartsComSQInfo(wBGACompanyDTO);
                }

                wBGAExamMapper.updatePartsCompany(wBGACompanyDTO);
                // 부품사 수정 End
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    public WBGAApplyDtlDTO stepUpdateProcess(WBGAApplyMstDTO wBGAApplyMstDTO, WBGAApplyDtlDTO wBGAApplyDtlDTO, WBGAMsEuipmentDTO wBGAMsEuipmentDTO) throws Exception {
        //검교정 단계별 분기 ( 1: 신청,  2: 심사   3:증빙 )
        if (wBGAApplyDtlDTO.getRsumeOrd() == 1) {
            //신청단계
            if ("PRO_TYPE07001_02_006".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_006");
                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);

                //PRO_TYPE07001_01_006 선정 다음스텝 생성.
                wBGAApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBGAApplyDtlDTO.setRsumeOrd(2);
                wBGAApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07002");
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_001");
                wBGAApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_04_001");

                wBGAExamMapper.insertApplyDtl(wBGAApplyDtlDTO);

            } else if ("PRO_TYPE07001_02_002".equals(wBGAApplyDtlDTO.getMngSttsCd()) || "PRO_TYPE07001_02_005".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE06001_02_002 보완요청, PRO_TYPE07001_02_005 미선정 _ 사유필요
                if ("PRO_TYPE07001_02_002".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                    wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_002");
                    wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
                } else {
                    wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_005");
                    wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
                }
                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);
            } else if ("PRO_TYPE07001_02_004".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                // PRO_TYPE07001_02_004 사용자취소
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_004");
                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);
            }
        } else if (wBGAApplyDtlDTO.getRsumeOrd() == 2) {
            //심사단계
            if ("PRO_TYPE07001_04_006".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_04_006 적합 다음스텝 생성
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_006");
                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);

                //PRO_TYPE07001_04_006 선정 다음스텝 생성.
                wBGAApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBGAApplyDtlDTO.setRsumeOrd(3);
                wBGAApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07003");
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_001");
                wBGAApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_06_001");

                wBGAExamMapper.insertApplyDtl(wBGAApplyDtlDTO);

            } else if ("PRO_TYPE07001_04_003".equals(wBGAApplyDtlDTO.getMngSttsCd()) || "PRO_TYPE07001_04_005".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_04_003 보완요청, PRO_TYPE07001_04_005 부적합
                if ("PRO_TYPE07001_04_003".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                    wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_003");
                    wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
                } else {
                    wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_005");
                    wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
                }

                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);
            }

            wBGAMsEuipmentDTO.setRegId(wBGAApplyDtlDTO.getRegId());
            wBGAMsEuipmentDTO.setRegIp(wBGAApplyDtlDTO.getRegIp());

            //심사단계 데이터처리(PK가 없어 삭제후 재등록)
            wBGAExamMapper.deleteMsEuipment(wBGAMsEuipmentDTO);
            wBGAExamMapper.insertMsEuipment(wBGAMsEuipmentDTO);

        } else if (wBGAApplyDtlDTO.getRsumeOrd() == 3) {
            //증빙단계
            if ("PRO_TYPE07001_06_003".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_003 보완요청
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_003");
                wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
            } else if ("PRO_TYPE07001_06_005".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_005 부적합
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_005");
                wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
            } else if ("PRO_TYPE07001_06_006".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_006 적합
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_006");
            }
            wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);
        }

        //파일처리
        if (wBGAApplyDtlDTO.getRsumeOrd() == 1 || wBGAApplyDtlDTO.getRsumeOrd() == 3) {
            if (wBGAApplyMstDTO.getFileList() != null) {
                wBGAExamMapper.deleteFileInfo(wBGAApplyDtlDTO);

                //신청파일 넣기
                for (int i = 0; i < wBGAApplyMstDTO.getFileList().size() ; i++) {
                    List<COFileDTO> fileList = new ArrayList();
                    COFileDTO fileDto = new COFileDTO();

                    fileDto.setStatus(wBGAApplyMstDTO.getFileList().get(i).getStatus());
                    fileDto.setWidth(wBGAApplyMstDTO.getFileList().get(i).getWidth());
                    fileDto.setHeight(wBGAApplyMstDTO.getFileList().get(i).getHeight());
                    fileDto.setWebPath(wBGAApplyMstDTO.getFileList().get(i).getWebPath());
                    fileDto.setFieldNm(wBGAApplyMstDTO.getFileList().get(i).getFieldNm());
                    fileDto.setOrgnFileNm(wBGAApplyMstDTO.getFileList().get(i).getOrgnFileNm());
                    fileDto.setFileDsc(wBGAApplyMstDTO.getFileList().get(i).getFileDsc());
                    fileDto.setFileOrd(wBGAApplyMstDTO.getFileList().get(i).getFileOrd());
                    fileList.add(fileDto);

                    if ("addedfile".equals(fileDto.getStatus())) {
                        wBGAApplyDtlDTO.setFileSeq(wBGAApplyMstDTO.getFileList().get(i).getFileSeq());
                    } else {
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                        wBGAApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    }

                    wBGAApplyDtlDTO.setFileCd(wBGAApplyMstDTO.getFileCdList().get(i));
                    wBGAExamMapper.insertFileInfo(wBGAApplyDtlDTO);
                }
            }
        }
        return  wBGAApplyDtlDTO;
    }

    public WBBATransDTO getTrnsfList(WBBATransDTO wbbaTransDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbbaTransDTO.getPageIndex());
        page.setRecordCountPerPage(wbbaTransDTO.getListRowSize());

        page.setPageSize(wbbaTransDTO.getPageRowSize());

        wbbaTransDTO.setFirstIndex(page.getFirstRecordIndex());
        wbbaTransDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wbbaTransDTO.setList(wBGAExamMapper.getTrnsfList(wbbaTransDTO));
        wbbaTransDTO.setTotalCount(wBGAExamMapper.getTrnsfCount(wbbaTransDTO));

        return wbbaTransDTO;
    }
}
