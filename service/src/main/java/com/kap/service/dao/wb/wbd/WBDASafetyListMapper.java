package com.kap.service.dao.wb.wbd;

import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;

import java.util.List;

public interface WBDASafetyListMapper {
    /**
     * 회차 조회
     */
    public List<WBRoundMstSearchDTO> selectCarbonList(WBRoundMstSearchDTO wBRoundMstSearchDTO);

    /**
     * 회차 전체 갯수
     */
    public int getCarbonListTotCnt(WBRoundMstSearchDTO wBRoundMstSearchDTO);

    /**
     * 회차 등록
     */
    public int insertCarbon(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 수정
     */
    public int updateCarbon(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 중복 수정
     */
    public int updateExpsYnCarbon(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 지급차수 등록
     */
    public int insertGiveList(WBOrderMstDto wBOrderMstDto) throws Exception;

    /**
     * 지급차수 수정
     */
    public int updateGiveList(WBOrderMstDto wBOrderMstDto) throws Exception;

    /**
     * 지급차수 조회
     */
    public List<WBOrderMstDto> selectGiveList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 삭제
     */
    public int deleteCarbon(WBRoundMstDTO wBRoundMstDTO) throws Exception;
    
    /**
     * 지급차수 삭제
     */
    public int deleteGiveList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectCarbonDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 삭제
     */
    public int getAppctnCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 리스트 삭제
     */
    public int carbonDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 중복 체크
     */
    public int episdChk(WBRoundMstDTO wBRoundMstDTO) throws Exception;

}
