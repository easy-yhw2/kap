package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.*;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.mp.mpi.MPIWthdrwDto;
import com.kap.service.COFileService;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.dao.COLgnMapper;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 일반 사용자 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPAUserServiceImpl.java
 * @Description		: 일반사용자 관리를 위한 ServiceImpl
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		양현우				   최초 생성
 *    2024.01.17		양현우				   a -> b -> a 이직 시 오류 수정
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPAUserServiceImpl implements MPAUserService {

    private final MPAUserMapper mpaUserMapper;

    private final COLgnMapper coLgnMapper;

    private final COSystemLogService cOSystemLogService;

    private final MPEPartsCompanyService mpePartsCompanyService;

    private final COFileService cOFileService;

    private final EgovIdGnrService memModSeqIdgen;

    private final EgovIdGnrService memSeqIdgen;

    /**
     * 사용자 조회
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    @Override
    public MPAUserDto selectUserList(MPAUserDto mpaUserDto) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpaUserDto.getPageIndex());
        page.setRecordCountPerPage(mpaUserDto.getListRowSize());

        page.setPageSize(mpaUserDto.getPageRowSize());

        mpaUserDto.setFirstIndex( page.getFirstRecordIndex() );
        mpaUserDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpaUserDto.setTotalCount( mpaUserMapper.selectUserListCnt(mpaUserDto ));
        List<MPAUserDto> mpaUserDtos = mpaUserMapper.selectUserList(mpaUserDto);
        mpaUserDto.setList( mpaUserDtos );

        return mpaUserDto;

    }

    /**
     * 일반 사용자 기본 정보 조회
     * @param mpaUserDto
     * @return
     */
    public MPAUserDto selectUserDtl(MPAUserDto mpaUserDto) {
        return mpaUserMapper.selectUserDtl(mpaUserDto);
    }

    /**
     * 일반 사용자 회원 상세 조회
     * @param mpaUserDto
     * @return
     */
    public MPAUserDto selectUserDtlTab(MPAUserDto mpaUserDto) {
        return mpaUserMapper.selectUserDtlTab(mpaUserDto);
    }


    /**
     * ci값 조회
     * @param mpaUserDto
     * @return
     */
    public MPAUserDto selectCiUser(MPAUserDto mpaUserDto) {
        return mpaUserMapper.selectCiUser(mpaUserDto);
    }


    /**
     * 이메일 중복 검사
     * @param mpaUserDto
     * @return
     */
    public int selectDupEmail(MPAUserDto mpaUserDto) {
        return mpaUserMapper.selectDupEmail(mpaUserDto);
    }

    /**
     * id 중복 검사
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    @Override
    public int selectDupId(MPAUserDto mpaUserDto) throws Exception {
        return mpaUserMapper.selectDupId(mpaUserDto);
    }

    /**
     * 회원 수정
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    public int updateUserDtl(MPAUserDto mpaUserDto) throws Exception {

        if (mpaUserDto.getChngFndn().equals("Y")) {
            mpaUserMapper.updateUserDtlModS(mpaUserDto);
        }

        if(mpaUserDto.getFileList() != null && !mpaUserDto.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(mpaUserDto.getFileList());
            mpaUserDto.setCmssrPhotoFileSeq(fileSeqMap.get("fileSeq"));
        }

        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);

        return mpaUserMapper.updateUserDtl(mpaUserDto);

    }


    /**
     * 미래차공모전 리스트 조회
     * @param mpaAttctnDto
     * @return
     * @throws Exception
     */
    @Override
    public MPAAttctnDto selectAttcntList(MPAAttctnDto mpaAttctnDto) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpaAttctnDto.getPageIndex());
        page.setRecordCountPerPage(mpaAttctnDto.getListRowSize());

        page.setPageSize(mpaAttctnDto.getPageRowSize());

        mpaAttctnDto.setFirstIndex( page.getFirstRecordIndex() );
        mpaAttctnDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpaAttctnDto.setTotalCount( mpaUserMapper.selectAttcntListCnt(mpaAttctnDto));
        List<MPAAttctnDto> mpaAttctnDtos = mpaUserMapper.selectAttcntList(mpaAttctnDto);
        mpaAttctnDto.setList( mpaAttctnDtos );

        return mpaAttctnDto;

    }

    /**
     * 문의 리스트 조회
     * @param mpaInqrDto
     * @return
     * @throws Exception
     */
    @Override
    public MPAInqrDto selectInqrList(MPAInqrDto mpaInqrDto) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpaInqrDto.getPageIndex());
        page.setRecordCountPerPage(mpaInqrDto.getListRowSize());

        page.setPageSize(mpaInqrDto.getPageRowSize());

        mpaInqrDto.setFirstIndex( page.getFirstRecordIndex() );
        mpaInqrDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpaInqrDto.setTotalCount( mpaUserMapper.selectInqrListCnt(mpaInqrDto));
        List<MPAInqrDto> mpaInprDtos = mpaUserMapper.selectInqrList(mpaInqrDto);
        mpaInqrDto.setList( mpaInprDtos );

        return mpaInqrDto;

    }

    /**
     * 비밀번호 변경
     * @param mpPwdInitDto
     * @return
     * @throws Exception
     */
    @Override
    public int updatePwdInit(MPPwdInitDto mpPwdInitDto) throws Exception {
        // 특수문자 포함하도록 변경
        String password = RandomStringUtils.random(8, 0, 0, true, true, null, new SecureRandom()) + RandomStringUtils.random(2, 35, 64, false, false, null, new SecureRandom());
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        // & < > : ; ? ' " 공백은 치환 -> !
        password = password.replaceAll("[\\s&<>:;?']", "!");

        MPPwdInitDto mpPwdInitDtos = mpaUserMapper.selectMemDtl(mpPwdInitDto);
        mpPwdInitDto.setPwd(password);
        mpPwdInitDto.setEmail(mpPwdInitDtos.getEmail());
        mpPwdInitDto.setMemCd(mpPwdInitDtos.getMemCd());
        mpPwdInitDto.setName(mpPwdInitDtos.getName());
        mpPwdInitDto.setRegId( cOUserDetailsDTO.getId() );
        mpPwdInitDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
        mpPwdInitDto.setModId( cOUserDetailsDTO.getId() );
        mpPwdInitDto.setModIp( cOUserDetailsDTO.getLoginIp() );
        mpPwdInitDto.setNewEncPwd(COSeedCipherUtil.encryptPassword(password, mpPwdInitDto.getId()));

        return mpaUserMapper.updatePwdInit(mpPwdInitDto);

    }

    /**
     * ci 중복 검사
     * @param mpaUserDto
     * @return
     */
    public int selectCiCnt(MPAUserDto mpaUserDto) {
        return mpaUserMapper.selectCiCnt(mpaUserDto);
    }

    /**
     * 부품사 갯수 조회
     * @param mpePartsCompanyDTO
     * @return
     */
    public int selectcmpnMst(MPEPartsCompanyDTO mpePartsCompanyDTO) {
        return mpaUserMapper.selectcmpnMst(mpePartsCompanyDTO);
    }

    /**
     * 사용자 부품사 등록
     * @param mpaUserDto
     * @throws Exception
     */
    @Override
    public void insertUser(MPAUserDto mpaUserDto , MPEPartsCompanyDTO mpePartsCompanyDTO , MPJoinDto mpJoinDto) throws Exception {
        int dupIdCnt = selectDupId(mpaUserDto);
        int dupCiCnt = selectCiCnt(mpaUserDto);
        int dupCmpnCnt = selectcmpnMst(mpePartsCompanyDTO);

        if(dupCiCnt == 0 && dupIdCnt == 0 ) {
            mpaUserDto.setEncPwd(COSeedCipherUtil.encryptPassword(mpaUserDto.getPwd(), mpaUserDto.getId()));
            mpaUserDto.setMemSeq(memSeqIdgen.getNextIntegerId());
            mpaUserMapper.insertUser(mpaUserDto);
            mpaUserMapper.insertUserDtl(mpaUserDto);
            if(mpaUserDto.getMemCd().equals("CP")) {
                mpaUserMapper.insertUserCmpnRel(mpaUserDto);
                if(mpJoinDto.getBsnmChk().equals("false") && dupCmpnCnt == 0) {
                    mpePartsCompanyService.insertPartsCompany(mpePartsCompanyDTO);
                }
            }

            mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
            mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        }
    }

    /**
     * [사용자] 정보 수정
     * @param mpaUserDto
     * @param mpePartsCompanyDTO
     * @param mpJoinDto
     * @throws Exception
     */
    @Override
    public void updateUser(MPAUserDto mpaUserDto, MPEPartsCompanyDTO mpePartsCompanyDTO, MPJoinDto mpJoinDto) throws Exception {
        int dupCmpnCnt = selectcmpnMst(mpePartsCompanyDTO);
        String pwdChg = "N";
        if(!mpaUserDto.getPwd().isEmpty()) {
            mpaUserDto.setEncPwd(COSeedCipherUtil.encryptPassword(mpaUserDto.getPwd(), mpaUserDto.getId()));
            pwdChg ="Y";
        }

        mpaUserDto.setChngPwd(pwdChg);
        mpaUserMapper.updateUserDtlMod(mpaUserDto);

        if(mpaUserDto.getChngFndn().equals("Y")){
            mpaUserMapper.updateUserDtlModS(mpaUserDto);
        }

        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);

        if(mpaUserDto.getMemCd().equals("CP")) {
            if(mpJoinDto.getPartTypeChg().equals("new")) {
                /**
                 * 24-01-17 추가
                 * 회사 관계 카운팅 없으면 ins 있으면 upd
                 */
                if(mpaUserMapper.selectCmpnRelCount(mpaUserDto) >= 1) {
                    mpaUserMapper.updateCmpnRel(mpaUserDto);
                } else {
                    mpaUserMapper.insertUserCmpnRel(mpaUserDto);

                }
            }
            if(mpJoinDto.getBsnmChk().equals("false") && dupCmpnCnt == 0) {
                mpePartsCompanyService.insertPartsCompany(mpePartsCompanyDTO);
            }
            Object loginMap = RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            if (loginMap != null && loginMap instanceof COUserDetailsDTO) {
                // 기존 속성이 COUserDetailsDTO 타입이면서 null이 아닌 경우
                COUserDetailsDTO tmpCOUserDetailsDTO = (COUserDetailsDTO) loginMap;

                // 업데이트할 로직 수행
                tmpCOUserDetailsDTO.setAuthCd(mpaUserDto.getMemCd());
                tmpCOUserDetailsDTO.setBsnmNo(mpaUserDto.getWorkBsnmNo());

                // 업데이트된 속성을 세션에 다시 저장
                RequestContextHolder.getRequestAttributes().setAttribute("loginMap", tmpCOUserDetailsDTO, RequestAttributes.SCOPE_SESSION);
            }
        }
    }


    /**
     * 사용자 부품사 수정
     * @param mpaUserDto
     * @param mpePartsCompanyDTO
     * @param mpJoinDto
     * @throws Exception
     */
    @Override
    public void updateUserCompanyChg(MPAUserDto mpaUserDto, MPEPartsCompanyDTO mpePartsCompanyDTO, MPJoinDto mpJoinDto) throws Exception {
        int dupCmpnCnt = selectcmpnMst(mpePartsCompanyDTO);
        mpaUserMapper.updateUserDtlModBsnm(mpaUserDto);
        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        mpaUserMapper.insertUserCmpnRel(mpaUserDto);

        if (mpJoinDto.getBsnmChk().equals("false") && dupCmpnCnt == 0) {
            mpePartsCompanyService.insertPartsCompany(mpePartsCompanyDTO);
        }

    }

    /**
     * 부품사 수정시 사업 조회
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    @Override
    public MPAPartDto selectConfirmComp(MPAUserDto mpaUserDto) throws Exception {
        MPAPartDto mpaPartDto = new MPAPartDto();
        int mpListCnt = mpaUserMapper.selectMPHPartList(mpaUserDto).size();
        int conList = mpaUserMapper.selectConListCnt(mpaUserDto);
        int totCnt = mpListCnt + conList;
        boolean chk = false;
        if(totCnt >= 1) {
            chk= true;
        } else {
            chk= false;
        }
        mpaPartDto.setChk(chk);
        mpaPartDto.setCount(totCnt);
        return mpaPartDto;
    }

    /**
     * 사용자 탈퇴
     * @param mpiWthdrwDto
     * @throws Exception
     */
    @Override
    public void updateUserWthdrw(MPIWthdrwDto mpiWthdrwDto) throws Exception {
            mpaUserMapper.updateUserWthdrw(mpiWthdrwDto);
            mpaUserMapper.insertUserWthdrw(mpiWthdrwDto);
            mpaUserMapper.updateUserCiDel(mpiWthdrwDto);
    }

    /**
     * 사용자 id조회
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    @Override
    public MPAUserDto selectUserDtlId(MPAUserDto mpaUserDto) throws Exception {
        return mpaUserMapper.selectUserDtlId(mpaUserDto);
    }

    /**
     * 엑셀 다운
     * @param mpaUserDto
     * @param response
     * @throws Exception
     */
    @Override
    public void excelDownload(MPAUserDto mpaUserDto, HttpServletResponse response) throws Exception {
        if (mpaUserDto.getMemCd().equals("CO")) {   //일반
            excelCo(mpaUserDto, response);
        } else if(mpaUserDto.getMemCd().equals("CP")) { //부품
            excelCp(mpaUserDto, response);
        } else if(mpaUserDto.getMemCd().equals("CS")) { //위원
            excelCs(mpaUserDto, response);
        }
    }

    /**
     * 위원 엑셀
     * @param mpaUserDto
     * @param response
     * @throws IOException
     */
    private void excelCs(MPAUserDto mpaUserDto, HttpServletResponse response) throws IOException {

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
        cell.setCellValue("아이디");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("이름");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("위원구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("업종/분야");
        cell.setCellStyle(style_header);


        cell = row.createCell(5);
        cell.setCellValue("재직여부");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("휴대폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("생년월일");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("입사일");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("퇴사일");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("주요경력");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("컨설팅분야");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("등록일");
        cell.setCellStyle(style_header);

        // Body
        List<MPAUserDto> list = mpaUserDto.getList();

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(mpaUserDto.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //아이디
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getId());
            cell.setCellStyle(style_body);

            //이름
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getName());
            cell.setCellStyle(style_body);

            //위원구분
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getCmssrTypeCdNm());
            cell.setCellStyle(style_body);

            //업종분야
            cell = row.createCell(4);
            cell.setCellValue(excelHypen(list.get(i).getCmssrCbsnCdNm()));
            cell.setCellStyle(style_body);

            //재직여부
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getCmssrWorkCdNm());
            cell.setCellStyle(style_body);

            //휴대폰
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //생일
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getBirth());
            cell.setCellStyle(style_body);

            //입사일
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getCmssrMplmnDt());
            cell.setCellStyle(style_body);

            //퇴사일
            cell = row.createCell(10);
            cell.setCellValue(excelHypen(list.get(i).getCmssrRsgntDt()));
            cell.setCellStyle(style_body);

            //주요경력
            cell = row.createCell(11);
            cell.setCellValue(excelHypen(StringEscapeUtils.unescapeHtml4(list.get(i).getCmssrMjrCarerCntn())));
            cell.setCellStyle(style_body);

            //컨설팅분야
            cell = row.createCell(12);
            cell.setCellValue(excelHypen(StringEscapeUtils.unescapeHtml4(list.get(i).getCmssrCnstgFldCntn())));
            cell.setCellStyle(style_body);

            //등록일
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getRegDtm() == null ? "-" : list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);

        }

        // 열 너비 설정
        for(int i =0; i < 14; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i)  + 2000));
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_위원관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("회원/부품사 관리 > 위원관리");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPAUserServiceImpl");
        pCoSystemLogDTO.setFncNm("selectUserList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(mpaUserDto.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);

    }

    /**
     * 일반 엑셀
     * @param mpaUserDto
     * @param response
     * @throws Exception
     */
    private void excelCo(MPAUserDto mpaUserDto ,HttpServletResponse response) throws Exception {
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
        cell.setCellValue("아이디");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("이름");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("성별");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("생년월일");
        cell.setCellStyle(style_header);


        cell = row.createCell(5);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("휴대폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("일반 전화번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("주소");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("우편번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("마케팅 활용동의");
        cell.setCellStyle(style_header);
        cell = row.createCell(11);
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("가입일");
        cell.setCellStyle(style_header);


        row = sheet.createRow(rowNum++);
        for (int i = 0; i <= 12; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style_header);

            if (i != 10 && i != 11) {
                sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i)); //열시작, 열종료, 행시작, 행종료 (자바배열과 같이 0부터 시작)
            } else if(i == 10){
                cell.setCellValue("E-MAIL");
            } else if(i == 11) {
                cell.setCellValue("SMS");
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10,11));

            // Body
        List<MPAUserDto> list = mpaUserDto.getList();

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(mpaUserDto.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //아이디
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getId());
            cell.setCellStyle(style_body);

            //이름
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getName());
            cell.setCellStyle(style_body);

            //성별
            cell = row.createCell(3);
            String gndrNm = "-";
            if(!StringUtils.isEmpty(list.get(i).getGndr())) {
                if (list.get(i).getGndr().equals("1")) {
                    gndrNm = "남";
                } else {
                    gndrNm = "여";
                }
            }
            cell.setCellValue(gndrNm);
            cell.setCellStyle(style_body);

            //생년월일
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getBirth());
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //휴대폰
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //전화번호
            cell = row.createCell(7);
            cell.setCellValue(excelHypen(list.get(i).getTelNo()));
            cell.setCellStyle(style_body);
            //주소
            cell = row.createCell(8);
            String bscAddr = list.get(i).getBscAddr() == null ? "-" : list.get(i).getBscAddr();
            String dtlAddr = list.get(i).getDtlAddr() == null ? "-" : list.get(i).getDtlAddr();
            cell.setCellValue(bscAddr + " " + dtlAddr);
            cell.setCellStyle(style_body);

            //우편번호
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getZipcode());
            cell.setCellStyle(style_body);

            //이메일 여부
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getNtfyEmailRcvYn().toString().equals("Y") ? "O" : "X");
            cell.setCellStyle(style_body);

            //SMS 여부
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getNtfySmsRcvYn().toString().equals("Y") ? "O" : "X");
            cell.setCellStyle(style_body);

            //가입일
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getRegDtm() == null ? "-" : list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);
        }

        // 열 너비 설정
        for(int i =0; i < 13; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i)  + 2000));
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_일반회원관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("회원/부품사 관리 > 일반회원관리");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPAUserServiceImpl");
        pCoSystemLogDTO.setFncNm("selectUserList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(mpaUserDto.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 부품사 엑셀
     * @param mpaUserDto
     * @param response
     * @throws Exception
     */
    private void excelCp(MPAUserDto mpaUserDto, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("아이디");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("이름");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("성별");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("생년월일");
        cell.setCellStyle(style_header);


        cell = row.createCell(5);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("휴대폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("일반 전화번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("부서");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("직급");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("주소");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("우편번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("마케팅 활용동의");
        cell.setCellStyle(style_header);
        cell = row.createCell(17);
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("가입일");
        cell.setCellStyle(style_header);


        row = sheet.createRow(rowNum++);
        for (int i = 0; i <= 18; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style_header);

            if (i != 16 && i != 17) {
                sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i)); //열시작, 열종료, 행시작, 행종료 (자바배열과 같이 0부터 시작)
            } else if(i == 16){
                cell.setCellValue("E-MAIL");
            } else if(i == 17) {
                cell.setCellValue("SMS");
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 16,17));

        // Body
        List<MPAUserDto> list = mpaUserDto.getList();

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(mpaUserDto.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //아이디
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getId());
            cell.setCellStyle(style_body);

            //이름
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getName());
            cell.setCellStyle(style_body);

            //성별
            String gndrNm = "-";
            if(!StringUtils.isEmpty(list.get(i).getGndr())) {
                if (list.get(i).getGndr().equals("1")) {
                    gndrNm = "남";
                } else {
                    gndrNm = "여";
                }
            }
            cell = row.createCell(3);
            cell.setCellValue(gndrNm);
            cell.setCellStyle(style_body);

            //생년월일
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getBirth());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getWorkBsnmNo() == null ? "-" : list.get(i).getWorkBsnmNo().substring(0,3)+"-"+list.get(i).getWorkBsnmNo().substring(3,5)+"-"+list.get(i).getWorkBsnmNo().substring(5));
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getCtgryCdNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getSizeCdNm());
            cell.setCellStyle(style_body);

            //휴대폰번호
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //일반 전화번호
            cell = row.createCell(10);
            cell.setCellValue(excelHypen(list.get(i).getTelNo()));
            cell.setCellStyle(style_body);

            //부서
            cell = row.createCell(11);
            cell.setCellValue(StringEscapeUtils.unescapeHtml4(list.get(i).getDeptCdNm())+"("+StringEscapeUtils.unescapeHtml4(list.get(i).getDeptDtlNm())+")");
            cell.setCellStyle(style_body);

            //직급
            cell = row.createCell(12);
            cell.setCellValue(excelHypen(list.get(i).getPstnCdNm()) +(list.get(i).getPstnCdNm().equals("기타") ? "("+StringEscapeUtils.unescapeHtml4(list.get(i).getPstnNm())+")" : ""));
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //주소
            cell = row.createCell(14);
            String bscAddr = list.get(i).getBscAddr() == null ? "-" : list.get(i).getBscAddr();
            String dtlAddr = list.get(i).getDtlAddr() == null ? "-" : list.get(i).getDtlAddr();
            cell.setCellValue(bscAddr + " " + dtlAddr);
            cell.setCellStyle(style_body);

            //우편번호
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getZipcode());
            cell.setCellStyle(style_body);

            //이메일 여부
            cell = row.createCell(16);
            String emailYn = "-";
            if(list.get(i).getNtfyEmailRcvYn()!= null) {
               if(list.get(i).getNtfyEmailRcvYn().toString().equals("Y")) {
                   emailYn = "O";
               } else {
                   emailYn ="X";
               }
            }
            cell.setCellValue(emailYn);
            cell.setCellStyle(style_body);
            String smsYn = "-";
            if(list.get(i).getNtfySmsRcvYn()!= null) {
                if(list.get(i).getNtfySmsRcvYn().toString().equals("Y")) {
                    smsYn = "O";
                } else {
                    smsYn ="X";
                }
            }
            //SMS 여부
            cell = row.createCell(17);
            cell.setCellValue(smsYn);
            cell.setCellStyle(style_body);

            //가입일 여부
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getRegDtm() == null ? "-" : list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);


        }

        // 열 너비 설정
        for(int i =0; i < 19; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i)  + 2000));
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_부품사회원_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("회원/부품사 관리 > 부품사회원 관리");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPAUserServiceImpl");
        pCoSystemLogDTO.setFncNm("selectUserList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(mpaUserDto.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 엑셀 하이픈
     * @param str
     * @return
     */
    private String excelHypen(String str) {
        return org.apache.commons.lang.StringUtils.isEmpty(str) ? "-" : str;
    }
}
