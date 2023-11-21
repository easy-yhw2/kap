package com.kap.core.dto.bd.bdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COFileDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  재단소식 마스터 DTO
 *
 * @author 장두석
 * @since 2023.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20    장두석              최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "재단소식 마스터")
public class BDBCompanyNewsDTO extends BaseDTO {

    @Schema(title = "재단소식 순번", example = "숫자")
    private Integer fndnNewsSeq;

    @Schema(title = "제목")
    private String titl;

    @Schema(title = "내용")
    private String cntn;

    @Schema(title = "파일순번")
    private Integer fileSeq;

    @Schema(title = "PC 썸네일 이미지 순번")
    private Integer pcThnlSeq;

    @Schema(title = "Mobile 썸네일 이미지 순번")
    private Integer mblThnlSeq;

    @Schema(title = "노출여부")
    private String expsYn;

    @Schema(title = "조회수")
    private Integer readCnt;


    @Schema(title = "노출여부 리스트")
    @Hidden
    private List<Character> expsYnList;

    @Schema(title = "조회 리스트")
    @Hidden
    private List<BDBCompanyNewsDTO> list;

    @Schema(title = "PC 썸네일 리스트")
    @Hidden
    private List<COFileDTO> pcThumbList;

    @Schema(title = "모바일 썸네일 리스트")
    @Hidden
    private List<COFileDTO> moThumbList;
}
