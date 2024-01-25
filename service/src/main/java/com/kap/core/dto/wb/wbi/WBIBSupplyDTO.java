package com.kap.core.dto.wb.wbi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "스마트공장구축 신청업체관리 검색")
public class WBIBSupplyDTO extends BaseDTO {

    /* 상생 */
    @Schema(title = "사업코드", example = "")
    private String bsnCd;
    @Schema(title = "회차순번", example = "")
    private String episdSeq;
    @Schema(title = "회차순번", example = "")
    private String optnCd;
    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;
    @Schema(title = "과제명 코드", example = "")
    private String taskCd;
    @Schema(title = "사업 유형 코드", example = "")
    private String bsnTypeCd;
    @Schema(title = "스마트화 현재코드", example = "")
    private String smtfnPrsntCd;
    @Schema(title = "스마트화 목표코드", example = "")
    private String smtfnTrgtCd;
    @Schema(title = "종된사업장번호", example = "")
    private String sbrdnBsnmNo;
    @Schema(title = "상세 진행상태 코드", example = "")
    private String rsumeSttsCd;
    @Schema(title = "관리자메모", example = "")
    private String admMemo;
    @Schema(title = "관리자메모수정시간", example = "")
    private String admMemoModDtm;
    @Schema(title = "신청사업자번호")
    private String appctnBsnmNo;
    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;
    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;

    /*상생 파일 상세*/
    @Schema(title = "파일순번", example = "숫자")
    private String fileCd;
    @Schema(title = "파일이름", example = "숫자")
    private String fileNm;
    @Schema(title = "파일순번", example = "숫자")
    private Integer fileSeq;


    /* 상생 Data */
    @Schema(title = "회차", example = "1")
    private String episd;
    @Schema(title = "진행상태코드", example = "")
    private String appctnSttsCd;
    @Schema(title = "진행상태", example = "")
    private String appctnSttsCdNm;
    @Schema(title = "관리자상태값코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태값", example = "")
    private String mngSttsCdNm;
    @Schema(title = "신청상태변경일시", example = "yyyy-MM-dd")
    private String appctnSttsChngDtm;
    @Schema(title = "관리상태변경일시", example = "yyyy-MM-dd")
    private String mngSttsChngDtm;

    /* 신청자 정보 */
    @Schema(title = "신청자 이름", example = "")
    private String memSeq;
    @Schema(title = "신청자 이름", example = "")
    private String name;
    @Schema(title = "신청자 ID", example = "")
    private String id;
    @Schema(title = "일반 전화번호", example = "")
    private String telNo;
    @Schema(title = "휴대번호", example = "")
    private String hpNo;
    @Schema(title = "이메일", example = "")
    private String email;
    @Schema(title = "부서코드", example = "")
    private String deptCd;
    @Schema(title = "부서상세명", example = "")
    private String deptDtlNm;
    @Schema(title = "직급코드", example = "")
    private String pstnCd;
    @Schema(title = "직급 상세명", example = "")
    private String pstnNm;
    @Schema(title = "반려사유", example = "")
    private String rtrnRsnCntn;

    /* 회사 정보*/
    @Schema(title = "사업자등록번호", example = "")
    private String bsnmNo;
    @Schema(title = "회사 명", example = "")
    private String cmpnNm;
    @Schema(title = "회사 대표자명", example = "")
    private String rprsntNm;
    @Schema(title = "회사 전화번호", example = "")
    private String compTel;       // 중복 컬럼 명
    @Schema(title = "설립일자", example = "yyyy-MM-dd")
    private String stbsmDt;       //설립일자
    @Schema(title = "구분 코드 값", example = "")
    private String ctgryCd;
    @Schema(title = "구분", example = "")
    private String ctgryCdNm; /* 코드 값 nm */
    @Schema(title = "규모 코드 값", example = "")
    private String sizeCd;
    @Schema(title = "규모", example = "")
    private String sizeCdNm; /* 코드 값 nm */
    @Schema(title = "우편변호", example = "")
    private String zipCode;
    @Schema(title = "우편변호", example = "")
    private String bscAddr;
    @Schema(title = "우편변호", example = "")
    private String dtlAddr;
    @Schema(title = "매출액 - 금액", example = "100")
    private int slsPmt;
    @Schema(title = "매출액 - 년도", example = "yyyy")
    private String slsYear;
    @Schema(title = "직원 수", example = "100")
    private int mpleCnt;          // 직원 수
    @Schema(title = "주요생산품 1", example = "")
    private String mjrPrdct1;
    @Schema(title = "주요생산품 2", example = "")
    private String mjrPrdct2;
    @Schema(title = "주요생산품 3", example = "")
    private String mjrPrdct3;
    @Schema(title = "품질5스타코드")
    private String qlty5StarCd;
    @Schema(title = "품질5스타년도")
    private String qlty5StarYear;

    @Schema(title = "납입5스타코드")
    private String pay5StarCd;
    @Schema(title = "납입5스타년도")
    private String pay5StarYear;

    @Schema(title = "기술5스타코드")
    private String tchlg5StarCd;
    @Schema(title = "기술5스타년도")
    private String tchlg5StarYear;

    /* Detail */
    @Schema(title = "담당위원코드", example = "")
    private String picCmssrSeq;
    @Schema(title = "담당위원이름", example = "")
    private String picName;
    @Schema(title = "공급업체명", example = "")
    private String offerCmpnCdNm;
    @Schema(title = "공급업체 사업자등록번호", example = "")
    private String offerBsnmNo;

    @Schema(title = "sq정보")
    private List<WBIBSupplyDTO> sqInfoList;

    @Schema(title = "업종순번")
    private Integer cbsnSeq;

    @Schema(title = "업종명")
    private String nm;

    @Schema(title = "점수")
    private Integer score;

    @Schema(title = "SQ 평가년도")
    private Integer year;

    @Schema(title = "인증주관사명")
    private String crtfnCmpnNm;

    List<WBIBSupplyDTO> list;

    @Schema(title = "파일리스트", example = "")
    private List<Integer> fileSeqList;
}
