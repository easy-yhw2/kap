package com.kap.service.impl.wb.wbl;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.sv.sva.SVASurveyQstnDtlDTO;
import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBLSurveyService;
import com.kap.service.dao.wb.wbl.WBLSurveyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 상생협력체감도조사 Service
		 * </pre>
		*
		* @ClassName		: SVASurveyService.java
		* @Description		: 상생협력체감도조사 Service
		* @author 박준희
		* @since 2023.11.20
		* @version 1.0
		* @see
 * @Modification Information
		* <pre>
 * 		since			author					description
		 *   ===========    ==============    =============================
		 *    2023.11.20		박준희					최초생성
		 * </pre>
		*/
@Slf4j
@Service
@RequiredArgsConstructor
public class WBLSurveyServiceImpl implements WBLSurveyService {

	//DAO
	private final WBLSurveyMapper wBLSurveyMapper;

	/* 상생협력체감도조사 시퀀스 */
	private final EgovIdGnrService cxAppctnRsumeSrvIdgen;


	/* 회차관리 시퀀스 */
	private final EgovIdGnrService cxCmpnEpisdMstIdgen;


	/**
	 *  목록을 조회한다.
	 */
	public WBLSurveyMstSearchDTO selectSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(wBLSurveyMstSearchDTO.getPageIndex());
		page.setRecordCountPerPage(wBLSurveyMstSearchDTO.getListRowSize());

		page.setPageSize(wBLSurveyMstSearchDTO.getPageRowSize());

		wBLSurveyMstSearchDTO.setFirstIndex( page.getFirstRecordIndex() );
		wBLSurveyMstSearchDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		wBLSurveyMstSearchDTO.setList( wBLSurveyMapper.selectSurveyList(wBLSurveyMstSearchDTO) );
		wBLSurveyMstSearchDTO.setTotalCount( wBLSurveyMapper.selectSurveyListCnt(wBLSurveyMstSearchDTO) );
		return wBLSurveyMstSearchDTO;
	}


	@Override
	public int insertSurveyList(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request) throws Exception {
		int respCnt = 0;
		int surveyMstIdgen = 0;

		String crtfnNo = "";
		String episdText ="";
		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		wBLSurveyMstInsertDTO.setRegIp(regIp);
		wBLSurveyMstInsertDTO.setRegId(regId);
		wBLSurveyMstInsertDTO.setModIp(regIp);
		wBLSurveyMstInsertDTO.setModId(regId);

		//등록
		surveyMstIdgen = cxAppctnRsumeSrvIdgen.getNextIntegerId();

		wBLSurveyMstInsertDTO.setCxstnSrvSeq( surveyMstIdgen );

		if (wBLSurveyMstInsertDTO.getEpisd() < 10){
			episdText = "0"+Integer.toString(wBLSurveyMstInsertDTO.getEpisd());
		}else{
			episdText = Integer.toString(wBLSurveyMstInsertDTO.getEpisd());
		}
		crtfnNo = wBLSurveyMstInsertDTO.getYear()+episdText+wBLSurveyMstInsertDTO.getBsnmRegNo();

		wBLSurveyMstInsertDTO.setCrtfnNo(crtfnNo);
		wBLSurveyMstInsertDTO.setPtcptCd("E");
		wBLSurveyMstInsertDTO.setCmpltnYn("N");

		respCnt = wBLSurveyMapper.insertSurveyMst( wBLSurveyMstInsertDTO );

		return respCnt;
	}

	@Override
	public int deleteSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {

		int	respCnt = wBLSurveyMapper.deleteSurveyMst(wBLSurveyMstSearchDTO);

		return respCnt;

	}

	@Override
	public WBLSurveyMstInsertDTO selectSurveyDtl(WBLSurveyMstSearchDTO wBLSurveyMstSearchDT) throws Exception {
		WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO = wBLSurveyMapper.selectSurveyDtl(wBLSurveyMstSearchDT);

		return wBLSurveyMstInsertDTO;
	}

	@Override
	public WBLEpisdMstDTO selectEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception {

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(wBLEpisdMstDTO.getPageIndex());
		page.setRecordCountPerPage(wBLEpisdMstDTO.getListRowSize());

		page.setPageSize(wBLEpisdMstDTO.getPageRowSize());

		wBLEpisdMstDTO.setFirstIndex( page.getFirstRecordIndex() );
		wBLEpisdMstDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		wBLEpisdMstDTO.setList( wBLSurveyMapper.selectEpisdList(wBLEpisdMstDTO) );
		wBLEpisdMstDTO.setTotalCount( wBLSurveyMapper.selectEpisdListCnt(wBLEpisdMstDTO) );

		return wBLEpisdMstDTO;
	}

	@Override
	public int deleteEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception {

		int	respCnt = wBLSurveyMapper.deleteEpisdList(wBLEpisdMstDTO);

		return respCnt;

	}


	@Override
	public int insertEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO, HttpServletRequest request) throws Exception {
		int respCnt = 0;
		int episdMstIdgen = 0;

		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		wBLEpisdMstDTO.setRegIp(regIp);
		wBLEpisdMstDTO.setRegId(regId);
		wBLEpisdMstDTO.setModIp(regIp);
		wBLEpisdMstDTO.setModId(regId);

		WBLEpisdMstDTO wBLEpisdMstCheck = wBLSurveyMapper.selectEpisdMst( wBLEpisdMstDTO );

		if (wBLEpisdMstCheck!=null){
			//수정
			wBLEpisdMstDTO.setCxstnCmpnEpisdSeq( wBLEpisdMstCheck.getCxstnCmpnEpisdSeq() );
			respCnt = wBLSurveyMapper.updateEpisdMst( wBLEpisdMstDTO );
		}else{
			//등록
			episdMstIdgen = cxCmpnEpisdMstIdgen.getNextIntegerId();
			wBLEpisdMstDTO.setCxstnCmpnEpisdSeq( episdMstIdgen );
			respCnt = wBLSurveyMapper.insertEpisdMst( wBLEpisdMstDTO );
		}

		return respCnt;
	}

	@Override
	public WBLEpisdMstDTO selectEpisdSurveyList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception {

		wBLEpisdMstDTO.setList( wBLSurveyMapper.selectEpisdSurveyList(wBLEpisdMstDTO) );

		return wBLEpisdMstDTO;
	}


	/**
	 * 샘플 엑셀 생성
	 */
	public void excelSampleDownload(HttpServletResponse response) throws Exception{

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
		cell.setCellValue("1차 부품사");
		cell.setCellStyle(style_header);

		cell = row.createCell(1);
		cell.setCellValue("1차 부품사 코드");
		cell.setCellStyle(style_header);

		cell = row.createCell(2);
		cell.setCellValue("2차 부품사");
		cell.setCellStyle(style_header);

		cell = row.createCell(3);
		cell.setCellValue("2차 부품사 코드");
		cell.setCellStyle(style_header);

		cell = row.createCell(4);
		cell.setCellValue("대표자명");
		cell.setCellStyle(style_header);

		cell = row.createCell(5);
		cell.setCellValue("담당자명");
		cell.setCellStyle(style_header);

		cell = row.createCell(6);
		cell.setCellValue("사업자등록번호");
		cell.setCellStyle(style_header);

		cell = row.createCell(7);
		cell.setCellValue("전화번호");
		cell.setCellStyle(style_header);

		cell = row.createCell(8);
		cell.setCellValue("이메일 주소");
		cell.setCellStyle(style_header);


		// Body
		row = sheet.createRow(rowNum++);

		cell = row.createCell(0);
		cell.setCellValue("KOMOS");
		cell.setCellStyle(style_body);

		cell = row.createCell(1);
		cell.setCellValue("LB20");
		cell.setCellStyle(style_body);

		cell = row.createCell(2);
		cell.setCellValue("경일산업");
		cell.setCellStyle(style_body);

		cell = row.createCell(3);
		cell.setCellValue("#268C");
		cell.setCellStyle(style_body);

		cell = row.createCell(4);
		cell.setCellValue("이길동");
		cell.setCellStyle(style_body);

		cell = row.createCell(5);
		cell.setCellValue("홍길동");
		cell.setCellStyle(style_body);

		cell = row.createCell(6);
		cell.setCellValue("123-45-67890");
		cell.setCellStyle(style_body);

		cell = row.createCell(7);
		cell.setCellValue("010-1234-5678");
		cell.setCellStyle(style_body);

		cell = row.createCell(8);
		cell.setCellValue("abc@abc.com");
		cell.setCellStyle(style_body);


		//컨텐츠 타입 및 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("SensitivitySurveyForm", "UTF-8") + ".xlsx");

		// Excel File Output
		workbook.write(response.getOutputStream());
		workbook.close();

	}

	@Override
	public int insertSurveyExcelList(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request) throws Exception {

		int respCnt = 0;

		List<Map<String, Object>> listMap = getListData(wBLSurveyMstInsertDTO.getWblListExcel(), 1, 8 );

		if (listMap.size()>0){
			for(int i = 0 ; i < listMap.size() ; i++) {

				wBLSurveyMstInsertDTO.setPartCmpnNm1(listMap.get(i).get("0").toString());
				wBLSurveyMstInsertDTO.setPartCmpnCd1(listMap.get(i).get("1").toString());
				wBLSurveyMstInsertDTO.setPartCmpnNm2(listMap.get(i).get("2").toString());
				wBLSurveyMstInsertDTO.setPartCmpnCd2(listMap.get(i).get("3").toString());
				wBLSurveyMstInsertDTO.setRprsntNm(listMap.get(i).get("4").toString().replaceAll("-",""));
				wBLSurveyMstInsertDTO.setPicNm(listMap.get(i).get("5").toString());
				wBLSurveyMstInsertDTO.setBsnmRegNo(listMap.get(i).get("6").toString());
				wBLSurveyMstInsertDTO.setTelNo(listMap.get(i).get("7").toString());
				wBLSurveyMstInsertDTO.setEmail(listMap.get(i).get("8").toString());

				respCnt = insertSurveyList(wBLSurveyMstInsertDTO, request);
			}
		}

		return respCnt;
	}


	public List<Map<String, Object>> getListData(MultipartFile file, int startRowNum, int columnLength) throws IOException {

		List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = null;
		OPCPackage opcPackage = null;
		try {
			opcPackage = OPCPackage.open(file.getInputStream());
			workbook = new XSSFWorkbook(opcPackage);

			// 첫번째 시트
			XSSFSheet sheet = workbook.getSheetAt(0);

			int rowIndex = 0;
			int columnIndex = 0;

			// 첫번째 행(0)은 컬럼 명이기 때문에 두번째 행(1) 부터 검색
			for (rowIndex = startRowNum; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);

				// 빈 행은 Skip
				if (row.getCell(0) != null) {

					Map<String, Object> map = new HashMap<String, Object>();

					int cells = columnLength;

					for (columnIndex = 0; columnIndex <= cells; columnIndex++) {
						XSSFCell cell = row.getCell(columnIndex);
						map.put(String.valueOf(columnIndex), getCellValue(cell));
					}

					excelList.add(map);
				}
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null){
				workbook.close();
			}
			if (opcPackage != null){
				opcPackage.close();
			}
			file.getInputStream().close();
		}

		return excelList;
	}

	public String getCellValue(XSSFCell cell) {

		String value = "";

		if(cell == null){
			return value;
		}

		switch (cell.getCellType()) {
			case STRING:
				value = cell.getStringCellValue();
				break;
			case NUMERIC:
				value = (int) cell.getNumericCellValue() + "";
				break;
			default:
				break;
		}
		return value;
	}

}