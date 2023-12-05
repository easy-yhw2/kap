package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.eb.ebh.EBHEduApplicantMstDTO;
import com.kap.service.COCommService;
import com.kap.service.COFileService;
import com.kap.service.EBHEduApplicantService;
import com.kap.service.dao.eb.EBHEduApplicantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 교육 신청자 관리 ServiceImpl
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.01  장두석         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBHEduApplicantServiceImpl implements EBHEduApplicantService {

    /** Mapper **/
    private final EBHEduApplicantMapper pEBHEduApplicantMapper;

    /** Service **/
    private final COCommService cOCommService;
    private final COFileService cOFileService;

    /**
     * 리스트 조회
     */
    public EBHEduApplicantMstDTO selectList(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pEBHEduApplicantMstDTO.getPageIndex());
        page.setRecordCountPerPage(pEBHEduApplicantMstDTO.getListRowSize());

        page.setPageSize(pEBHEduApplicantMstDTO.getPageRowSize());

        pEBHEduApplicantMstDTO.setFirstIndex( page.getFirstRecordIndex() );
        pEBHEduApplicantMstDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        pEBHEduApplicantMstDTO.setTotalCount( pEBHEduApplicantMapper.getListCnt(pEBHEduApplicantMstDTO));
        pEBHEduApplicantMstDTO.setList( pEBHEduApplicantMapper.selectList(pEBHEduApplicantMstDTO) );

        return pEBHEduApplicantMstDTO;
    }

    /**
     * 상세 조회
     */
    public EBHEduApplicantMstDTO selectView(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception{

        return pEBHEduApplicantMstDTO;
    }

    /**
     * 수정
     */
    public int update(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception{

        return 0;
    }

}
