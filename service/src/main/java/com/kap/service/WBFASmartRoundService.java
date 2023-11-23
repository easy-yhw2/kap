package com.kap.service;

import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 스마트공장구축 회차 Service
 * </pre>
 *
 * @ClassName		: WBFASmartRoundService.java
 * @Description		: 스마트공장구축 회차 Service
 * @author 김동현
 * @since 2023.11.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.17		김동현				   최초 생성
 * </pre>
 */
public interface WBFASmartRoundService {

    /**
     * 회차 List 조회한다.
     */
    public WBRoundMstSearchDTO selApplyCompanyList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 글 상세 조회한다.
     */
    public WBRoundMstDTO selRoundDetail(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다. - 회차 관리 등록 페이지
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 관리 글 등록
     */
    public int insertRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 회차 관리 글 수정
     */
    public int updateRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 회차 관리 글 삭제
     */
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception;
}
