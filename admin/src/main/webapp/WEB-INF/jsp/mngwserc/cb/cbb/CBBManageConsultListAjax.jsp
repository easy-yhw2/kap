<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">
            <input type="checkbox" value="${list.cnstgSeq}" name="delValueList" class="checkboxSingle notRequired" data-edctn_seq="${list.cnstgSeq}"/>
            <span class="ion-checkmark-round"></span>
          </label>
        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>    <%--번호--%>
        <td class="text-center">${ list.bsnYear }</td>                                              <%--사업연도--%>
        <td class="text-center">${ list.resumeSttsNm }</td>                                         <%--진행상태--%>
        <td class="text-center">${ list.cmpnNm }</td>                                               <%--부품사명--%>
        <td class="text-center">${ list.appctnBsnmNo }</td>                                         <%--사업자등록번호--%>
        <td class="text-center">${ list.ctgryNm }</td>                                              <%--구분--%>
        <td class="text-center">${ list.sizeNm }</td>                                               <%--규모--%>
        <td class="text-center">${ list.slsPmt }</td>                                               <%--매출액(억원)--%>
        <td class="text-center">${ list.mpleCnt }</td>                                              <%--직원수--%>
        <td class="text-center">${ list.appctnFldNm }</td>                                          <%--신청분야--%>
        <td class="text-center">${ list.firstRgnsNm } ${ list.scndRgnsNm }</td>                     <%--신청소재지--%>
        <td class="text-center">${ list.crtfnCmpnNm }</td>                                          <%--SQ 인증 주관사--%>
        <td class="text-center">${ list.mnCmpnNm }</td>                                             <%--주고객사--%>
        <td class="text-center">${ empty list.vstDt ? '-' : list.vstDt }</td>                       <%--방문일--%>
        <td class="text-center">${ empty list.cmssrNm ? '-' : list.cmssrNm}</td>                    <%--담당위원--%>
        <td class="text-center">${ empty list.cmssrCbsnNm ? '-' : list.cmssrCbsnNm }</td>           <%--담당위원 업종/분야--%>
        <td class="text-center">${ empty list.cnstgKickfDt ? '-' : list.cnstgKickfDt }</td>         <%--킥오프일--%>
        <td class="text-center">  <%--킥오프자료--%>
          <c:choose>
            <c:when test="${empty list.kickfFileSeq}">
              -
            </c:when>
            <c:otherwise>
              <a href="/mngwserc/file/view?fileSeq=${list.kickfFileSeq}&fileOrd=${list.kickfFileOrd}">파일</a>
            </c:otherwise>
          </c:choose>
        </td>
        <td class="text-center">${ empty list.lvlupDt ? '-' : list.lvlupDt}</td>                    <%--렙업일--%>
        <td class="text-center">  <%--렙업자료--%>
          <c:choose>
            <c:when test="${empty list.kickfFileSeq}">
              -
            </c:when>
            <c:otherwise>
              <a href="/mngwserc/file/view?fileSeq=${list.lvlupFileSeq}&fileOrd=${list.lvlupFileOrd}">파일</a>
            </c:otherwise>
          </c:choose>
        </td>
        <td class="text-center">${ list.appctnDt }</td>                                            <%--신청일--%>
        <td class="text-center">${ list.regName }(${ list.regId })</td>                            <%--최초 등록자(아이디)--%>
        <td class="text-center">${ list.regDtm }</td>                                              <%--최초 등록일시--%>
        <td class="text-center">    <%--최종 수정자(아이디)--%>
            <c:choose>
              <c:when test="${empty list.modId}">
                -
              </c:when>
              <c:otherwise>
                ${ list.modName }(${ list.modId })
              </c:otherwise>
            </c:choose>
        </td>
        <td class="text-center">${ empty list.modDtm ? '-' : list.modDtm}</td>                    <%--최종 수정일시--%>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="26" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>