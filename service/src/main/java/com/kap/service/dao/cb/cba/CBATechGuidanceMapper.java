package com.kap.service.dao.cb.cba;

import com.kap.core.dto.cb.cba.CBAConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceUpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
/**
 * <pre>
 * 컨설팅 기술 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: CBATechGuidanceMapper.java
 * @Description		: 컨설팅 기술 관리를 위한 DAO
 * @author 임서화
 * @since 2023.10.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.16		임서화				   최초 생성
 * </pre>
 */
@Mapper
public interface CBATechGuidanceMapper {

    /**
     * 컨설팅 기술 지도 목록 조회
     */
    public List<CBATechGuidanceInsertDTO> selectTechGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 상세 조회
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 고객사 비율 상세 조회
     */
    public List<CBATechGuidanceInsertDTO> selectCnstgDlvryInfo(Integer cnstgSeq) throws Exception;
    /**
     * 완성차 의존 비율 상세 조회
     */
    public List<CBATechGuidanceInsertDTO> selectCnstgDpndnInfo(Integer cnstgSeq) throws Exception;

    /**
     * 컨설팅 기술 지도 카운트
     */
    public int selectTechGuidanceTotCnt(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 회사 업종 상세 카운트
     */
    public int selectCmpnCbsnInfoCnt(Integer cbsnSeq) throws Exception;

    /**
     * 컨설팅 기술 지도 수정
     */
    public int updateTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 진행 마스터 등록
     */
    public int insertTechGuidanceRsume(CBATechGuidanceUpdateDTO pCBATechGuidanceupUpdateDTO) throws Exception;
    /**
     * 컨설팅 진행 마스터 수정
     */
    public int updateTechGuidanceRsume(CBATechGuidanceUpdateDTO pCBATechGuidanceupUpdateDTO) throws Exception;

    /**
     * 컨설팅 진행 사항
     */
    public List<String> selectTechGuidanceRsume(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 삭제
     */
    public int deleteTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 부품사 업종 상세 수정
     */
    public int updateCbsnDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO);

    /**
     *  부품사 업종 수정
     */
    public int updateCbsnDtl(HashMap cbsnCdMap);

    /**
     *  부품사 업종 카운트
     */
    public int selectCnstgDpndnInfoCnt(HashMap cbsnCdMap);

    /**
     *  고객사 비율 수정
     */
    public int updateCnstgDlvryInfo(HashMap cnstgDlyvMap);

    /**
     * 완성차 의존 수정
     */
    public int updateCnstgDpndnInfo(HashMap dpndnMap);

    /**
     * 부품사 업종 상세 등록
     */
    public int insertCbsnDtl(HashMap cbstgCdMap);

    /**
     * 고객사 비율 상세 등록
     */
    public int insertCnstgDlvryInfo(HashMap dpndnMap);

    /**
     * 완성차 의존 매출 등록
     */
    public int insertCnstgDpndnInfo(HashMap dpndnMap);

    /**
     * 신청 분야 상세 등록
     */
    public int insertCnstgAppctnType(HashMap appctnTypeMap);

    /**
     * 신청 분야 상세 삭제
     */
    public int deleteCnstgAppctnType(HashMap appctnTypeMap);

    /**
     * 신청 분야 상세
     */
    public List<CBATechGuidanceInsertDTO> selectCnstgAppctnType(Integer cnstgSeq);
    
    /**
     * 만족도 상세
     */
    public List<CBATechGuidanceInsertDTO> selectTechGuidanceSurvey(Integer cnstgSeq);

    /**
     * 신청 부품사 카운트
     */
    public int selectRsumeTotCnt(Integer cnstgSeq) throws Exception;

    /**
     * 컨설팅 이관 내역 카운트
     */
    public int selectTrsfGuidanceCnt(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 이관 내역 등록
     */
    public int insertTrsfGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 만족도 종합결과 리스트 조회
     */
    public List<CBAConsultSuveyRsltListDTO> selectConsultSuveyRsltList(CBAConsultSuveyRsltListDTO cBAConsultSuveyRsltListDTO);

    /**
     * 만족도 종합결과 리스트 갯수 조회
     */
    public int getConsultSuveyRsltCnt(CBAConsultSuveyRsltListDTO cBAConsultSuveyRsltListDTO);

    /**
     * 컨설팅 이관 목록 조회
     */
    public  List<CBATechGuidanceInsertDTO> selectTrsfGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;
}
