package com.kap.core.dto.wb.wbf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.*;
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
public class WBFBRegisterSearchDTO extends BaseDTO {

    @Schema(title = "사업코드", example = "")
    private String bsnCd;
    @Schema(title = "옵션 구분 코드", example = "")
    private String optnCd;
    @Schema(title = "회차순번", example = "")
    private Integer episdSeq;

    /* List Data */
    @Schema(title = "사업년도", example = "YYYY")
    private String year;
    @Schema(title = "회차", example = "1")
    private String episd;
    @Schema(title = "사업년도", example = "YYYY")
    private Integer appctnSeq;
    @Schema(title = "신청상태코드", example = "")
    private String appctnSttsCd;
    @Schema(title = "신청상태코드 명", example = "") /* 코드 값 nm */
    private String appctnSttsCdNm;
    @Schema(title = "관리자상태값코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태값", example = "") /* 코드 값 nm */
    private String mngSttsCdNm;
    @Schema(title = "부품사명", example = "")
    private String cmpnNm;
    @Schema(title = "구분 코드 값", example = "")
    private String ctgryCd;
    @Schema(title = "구분", example = "") /* 코드 값 nm */
    private String ctgryCdNm;
    @Schema(title = "규모 코드 값", example = "")
    private String sizeCd;
    @Schema(title = "규모", example = "") /* 코드 값 nm */
    private String sizeCdNm;
    @Schema(title = "사업자등록번호", example = "")
    private String bsnmNo;
    @Schema(title = "신청자 이름", example = "")
    private String name;
    @Schema(title = "신청자 ID", example = "")
    private String id;
    @Schema(title = "휴대번호", example = "")
    private String hpNo;
    @Schema(title = "이메일", example = "")
    private String email;
    @Schema(title = "진행상태 코드", example = "")
    private String rsumeSttsCd;
    @Schema(title = "진행상태 명", example = "")
    private String rsumeSttsCdNm;
    @Schema(title = "신청상태변경일시", example = "yyyy-MM-dd")
    private String appctnSttsChngDtm;
    @Schema(title = "관리상태변경일시", example = "yyyy-MM-dd")
    private String mngSttsChngDtm;
    @Schema(title = "수정ID", example = "")
    private String modId;
    @Schema(title = "수정일시", example = "yyyy-MM-dd")
    private String modDtm;
    @Schema(title = "담당위원코드", example = "")
    private String picCmssrSeq;
    @Schema(title = "담당위원이름", example = "")
    private String picName;
    @Schema(title = "공급업체명", example = "")
    private String offerCmpnCdNm;
    @Schema(title = "공급업체 사업자등록번호", example = "")
    private String offerBsnmNo;

    /* 검색 관련 코드 */
    @Schema(title = "공통", example = "yyyy-MM-dd hh:mm:ss")
    private String carbonDate;

    @Schema(title = "구분 코드 리스트")
    private List ctgryCdList;

    @Schema(title = "검색 리스트", example = "")
    List<WBFBRegisterSearchDTO> list;

    /* 회차, 신청자, 부품사 정보 */
    WBFBRegisterDTO registerDtl;

    /* 회차 옵션 DTO */
    WBRoundMstDTO roundMstDTO;

    List<String> episdList;

    /* 상생신청지원금 상세 DTO */
    List<WBSpprtDtlDTO> spprtDtlList;

    /* 스마트 공장 상세 DTO */
    List<WBFBRsumeTaskDtlDTO> rsumeTaskDtlList;

    @Schema(title = "SQ 리스트 - 회사업종상세 테이블")
    private List<WBCompanyDetailMstDTO> searchSqInfoList;

    @Schema(title = "지급차수 List")
    private List<WBOrderMstDto> orderList;
}
