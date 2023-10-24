package com.kap.service.impl;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.service.CODSysLogService;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.CODSysLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <pre>
 * 접속로그관리를 위한 Service
 * </pre>
 *
 * @ClassName		: CODSysLogServiceImpl.java
 * @Description		: 접속로그관리를 위한 Service
 * @author 손태주
 * @since 2022.02.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2022.02.17		손태주					최초생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CODSysLogServiceImpl  implements CODSysLogService {
    //DAO
    private final CODSysLogMapper cODSysLogMapper;

    //로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    /**
     * 접속 로그 목록을 조회한다.
     */
    public COSystemLogDTO selectSysLogList(COSystemLogDTO pCODSysLogDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pCODSysLogDTO.getPageIndex());
        page.setRecordCountPerPage(pCODSysLogDTO.getListRowSize());
        page.setPageSize(pCODSysLogDTO.getPageRowSize());

        pCODSysLogDTO.setFirstIndex(page.getFirstRecordIndex());
        pCODSysLogDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pCODSysLogDTO.setList(cODSysLogMapper.selectSysLogList(pCODSysLogDTO));
        pCODSysLogDTO.setTotalCount(cODSysLogMapper.getSysLogListCnt(pCODSysLogDTO));

        return pCODSysLogDTO;
    }

    /**
     * 엑셀 생성
     */
    public void excelDownload(COSystemLogDTO pCODSysLogDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("소속");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("메뉴");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("처리구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("사유");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("IP");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("처리일자");
        cell.setCellStyle(style_header);

        // Body
        List<COSystemLogDTO> list = pCODSysLogDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(pCODSysLogDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //아이디
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getRegId());
            cell.setCellStyle(style_body);

            //이름
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getRegName());
            cell.setCellStyle(style_body);

            //부서
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getDeptCdNm());
            /*cell.setCellValue(list.get(i).getDeptNm());*/
            cell.setCellStyle(style_body);

            //메뉴
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getTrgtMenuNm().replaceAll("&gt;", ">"));
            cell.setCellStyle(style_body);

            //처리구분
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getPrcsCdNm());
            cell.setCellStyle(style_body);

            //사유
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getRsn() == null ? "-" : list.get(i).getRsn());
            cell.setCellStyle(style_body);

            //IP
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getRegIp());
            cell.setCellStyle(style_body);

            //처리일자
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getRegDtm()==null ? "-" : list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_이용로그관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();

        pCODSysLogDTO.setTrgtMenuNm("시스템 관리 > 로그 관리 > 이용로그관리");
        pCODSysLogDTO.setSrvcNm("mngwserc.co.cob.service.impl.CODSysLogServiceImpl");
        pCODSysLogDTO.setFncNm("selectSysLogList");
        pCODSysLogDTO.setPrcsCd("DL");
        pCODSysLogDTO.setRsn(pCODSysLogDTO.getRsn());
        pCODSysLogDTO.setRegId(lgnCOAAdmDTO.getId());
        pCODSysLogDTO.setRegIp(lgnCOAAdmDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCODSysLogDTO);
    }
}
