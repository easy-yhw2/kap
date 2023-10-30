<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td>
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.seq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td>${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <c:if test="${list.category eq 'image'}">
                  <td>이미지</td>
                </c:if>
                <c:if test="${list.category eq 'video'}">
                  <td>비디오</td>
                </c:if>
                <td>
                    <a href="javascript:" class="listView"  data-details-key="${list.seq}">
                            ${list.titl}
                    </a>
                </td>
                <td class="text-center" >${ list.odtmYn == 'Y' ? "상시" : kl:convertDate(list.strtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') +=  '</br> ~ </br>' +=  kl:convertDate(list.endDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } </td>
                <td class="text-center" >${ list.regNm }</td>
                <td class="text-center" data-reg-dtm="${list.regDtm}">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center" >${ list.modNm }</td>
                <td class="text-center" data-list-dtm="${list.modDtm}">${ kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center" data-use-yn="${list.mainYn}">${ list.mainYn eq 'Y' ? '노출' : '미노출' }</td>
                <td class="text-center" data-key="${list.seq}" data-value="${list.ord}">
                    <button type="button" class="btn btn-default btn-xs sortUp" name="sortUp" id="btnSort"><i class="ion-arrow-up-b"></i></button>
                    <button type="button" class="btn btn-default btn-xs ml-sm sortDown" name="sortDown" id="btnSort"><i class="ion-arrow-down-b"></i></button>
                </td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="8" class="text-center">
                등록된 데이터가 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>

