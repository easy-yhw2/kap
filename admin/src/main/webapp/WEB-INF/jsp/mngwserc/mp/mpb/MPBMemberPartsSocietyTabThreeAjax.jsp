<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center">${ list.bsnYear}</td>
                <td class="text-center">${ list.cnstgCdNm}</td>
                <td class="text-center">${ list.statusNm}</td>
                <td class="text-center">${ list.cmpnNm} </td>
                <td class="text-center">${ list.ctgryCdNm}</td>
                <td class="text-center">${ list.cmpnSizeCdNm}</td>
                <td class="text-center">${ kl:bsnmNoConvert(list.appctnBsnmNo)}</td>
                <td class="text-center">${ list.appctnFldCd}  TODO 양현우 코드?</td>
                <td class="text-center">${list.rgnsCdNm}  TODO 양현우 코드?</td>
                <td class="text-center">${list.name}</td>
                <td class="text-center">${list.appctnDt}</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="12" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>
