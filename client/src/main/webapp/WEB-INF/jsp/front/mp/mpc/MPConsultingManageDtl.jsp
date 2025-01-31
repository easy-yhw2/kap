<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/mp/mpc/MPConsultingDtlCtrl">
    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">컨설팅 사업 <br/>신청내역</span></p>
        </div>
    </div>
    <div class="divide-con-area" >
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3"></p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="flex">
                                        <input type="hidden" name="bsnYear" class="bsnYear" value="${rtnData.bsnYear}">
                                        <p class="f-title1">${rtnData.bsnYear} <br class="only-mobile"/>상주${rtnData.cnstgNm}</p>
                                        <div class="group">
                                            <c:choose>
                                                <c:when test="${manageDto.rsumeSttsCd eq 'MNGTECH_STATUS10' || manageDto.rsumeSttsCd eq 'MNGTECH_STATUS11' || manageDto.rsumeSttsCd eq 'MNGTECH_STATUS12'}">
                                                    <p class="f-head">지도단계</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="f-head">신청단계</p>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${fn:contains(manageDto.rsumeSttsNm, '탈락')}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger arr"><span>${manageDto.rsumeSttsNm}</span></p>
                                                    </div>
                                                </c:when>
                                                <c:when test="${manageDto.rsumeSttsNm eq '경영컨설팅이관' or manageDto.rsumeSttsNm eq '지도연기' or manageDto.rsumeSttsNm eq '이관'}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger waiting">
                                                            <span>${manageDto.rsumeSttsNm}</span>
                                                        </p>
                                                    </div>
                                                </c:when>
                                                <c:when test="${manageDto.rsumeSttsNm eq '신청' or manageDto.rsumeSttsNm eq '사용자취소' or manageDto.rsumeSttsNm eq '지도착수' or manageDto.rsumeSttsNm eq '재단취소' or manageDto.rsumeSttsNm eq '부품사취소'}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger">
                                                            <span>${manageDto.rsumeSttsNm}</span>
                                                        </p>
                                                    </div>
                                                </c:when>
                                                <c:when test="${manageDto.rsumeSttsNm eq '사전심사탈락' or manageDto.rsumeSttsNm eq '지도불가'}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger arr">
                                                            <span>${manageDto.rsumeSttsNm}</span>
                                                        </p>
                                                    </div>
                                                </c:when>
                                                <c:when test="${manageDto.rsumeSttsNm eq '사전심사선정' or manageDto.rsumeSttsNm eq '지도승인' or manageDto.rsumeSttsNm eq '지도중'}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger accepting">
                                                            <span>${manageDto.rsumeSttsNm}</span>
                                                        </p>
                                                    </div>
                                                </c:when>
                                                <c:when test="${manageDto.rsumeSttsNm eq '지도완료'}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger complete">
                                                            <span>${manageDto.rsumeSttsNm}</span>
                                                        </p>
                                                    </div>
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="def-list-w">
                                        <div class="def-list">
                                            <p class="tit f-head">신청일자</p>
                                            <p class="txt f-sub-head">${kl:convertDate(rtnData.appctnDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '-')}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:set var="survey" value="" />
                    <c:if test="${srvCnt > 0 && not empty rtnData.srvSeq && not empty rtnData.srvStrtDtm && not empty rtnData.srvEndDtm}"> <!-- 설문 응답 가능 기간에 포함되는 설문-->
                        <c:if test="${rspnCnt < 1 }"> <!-- 설문 조사 참여 여부 -->
                            <c:set var="survey" value="survey" />
                            <div class="cont-sec no-border scroll-motion">
                                <div class="for-motion">
                                    <div class="sec-tit-area">
                                        <p class="f-title3">컨설팅 만족도 설문 조사</p>
                                    </div>
                                    <div class="sec-con-area">
                                        <div class="graphic-sec">
                                            <div class="box-btn-area">
                                                <div class="bg-area">
                                                    <div class="img" style="background-image: url('/common/images/img-assessment-btn-bg.jpg');"></div>
                                                </div>
                                                <div class="txt-area">
                                                    <p class="txt f-head">신청하신 컨설팅 사업에 대한 만족도 설문 조사에 참여해주세요.</p>
                                                </div>
                                                <div class="btn-wrap">
                                                    <a class="btn-solid small white-bg survey" data-seq="${rtnData.cnstgSeq}" href="javascript:"><span>참여하기</span></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">신청자 기본정보 </p>
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>신청자 기본 정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>신청자</th><!-- 2023-12-12 텍스트 수정 -->
                                                <td>${rtnData.name}</td>
                                            </tr>
                                            <tr>
                                                <th>휴대폰번호</th>
                                                <td>${rtnData.hpNo}</td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>${rtnData.email}</td>
                                            </tr>
                                            <!-- 2023-12-22 수정 -->
                                            <tr>
                                                <th>일반 전화번호</th>
                                                <td><c:if test="${not empty rtnData.telNo}">${rtnData.telNo}</c:if><c:if test="empty rtnData.telNo">-</c:if></td>
                                            </tr>
                                            <!-- // 2023-12-22 수정 -->
                                            <tr>
                                                <th>부서</th>
                                                <td>${rtnData.deptNm}(${rtnData.deptDtlNm})</td>
                                            </tr>
                                            <tr>
                                                <th>직급</th>
                                                <td>
                                                    ${not empty rtnData.pstnCdNm ? rtnData.pstnCdNm : "-"}
                                                    <c:if test="${rtnData.pstnCd eq 'MEM_CD01007'}" >
                                                        (${rtnData.pstnNm})
                                                    </c:if>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">소속 부품사 기본정보</p><!-- 2023-12-12 텍스트 수정 -->
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>소속 부품사 기본정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>사업자등록번호</th>
                                                <td>${kl:bsnmNoConvert(rtnData.bsnmNo)}</td>
                                            </tr>
                                            <tr>
                                                <th>부품사명</th>
                                                <td>${rtnData.cmpnNm}</td>
                                            </tr>
                                            <tr>
                                                <th>대표자명</th>
                                                <td>${rtnData.rprsntNm}</td>
                                            </tr>
                                            <tr>
                                                <th>구분</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${rtnData.ctgryCd eq 'COMPANY01001'}">1차</c:when>
                                                        <c:when test="${rtnData.ctgryCd eq 'COMPANY01002'}">2차</c:when>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>규모</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${rtnData.sizeCd eq 'COMPANY02001'}">대기업</c:when>
                                                        <c:when test="${rtnData.sizeCd eq 'COMPANY02002'}">중견기업</c:when>
                                                        <c:when test="${rtnData.sizeCd eq 'COMPANY02003'}">중소기업</c:when>
                                                        <c:otherwise>기타</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>설립일자</th>
                                                <td>${rtnData.stbsmDt}</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>${rtnData.cmpnTelNo}</td>
                                            </tr>
                                            <tr>
                                                <th>본사주소</th>
                                                <td>(${rtnData.zipcode}) ${rtnData.bscAddr} ${rtnData.dtlAddr}</td>
                                            </tr>
                                            <tr>
                                                <th>매출액</th>
                                                <td>${rtnData.slsPmt}(${rtnData.slsYear})</td>
                                            </tr>
                                            <tr>
                                                <th>직원수</th>
                                                <td>${rtnData.mpleCnt}</td>
                                            </tr>
                                            <tr>
                                                <th>주생산품</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${empty rtnData.mjrPrdct1 and empty rtnData.mjrPrdct2 and empty rtnData.mjrPrdct3}">
                                                            -
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:if test="${not empty rtnData.mjrPrdct1}">
                                                                ① ${rtnData.mjrPrdct1}
                                                            </c:if>
                                                            <c:if test="${not empty rtnData.mjrPrdct2}">
                                                                ② ${rtnData.mjrPrdct2}
                                                            </c:if>
                                                            <c:if test="${not empty rtnData.mjrPrdct3}">
                                                                ③ ${rtnData.mjrPrdct3}
                                                            </c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <c:choose>
                                                <c:when test="${rtnData.ctgryCd eq 'COMPANY01001'}">
                                                    <c:forEach var="info" items="${cmpnInfo.list}" varStatus="status">
                                                        <tr>
                                                            <th>품질5스타</th>
                                                            <td>
                                                                <c:if test="${not empty info.qlty5StarCdNm}">
                                                                    ${info.qlty5StarCdNm} / ${info.qlty5StarYear}년
                                                                </c:if>
                                                                <c:if test="${empty info.qlty5StarCdNm}">
                                                                    -
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>납입5스타</th>
                                                            <td>
                                                                <c:if test="${not empty info.pay5StarCdNm}">
                                                                    ${info.pay5StarCdNm} / ${info.pay5StarYear}년
                                                                </c:if>
                                                                <c:if test="${empty info.pay5StarCdNm}">
                                                                    -
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>기술5스타</th>
                                                            <td>
                                                                <c:if test="${not empty info.tchlg5StarCdNm}">
                                                                    ${info.tchlg5StarCdNm} / ${info.tchlg5StarYear}년
                                                                </c:if>
                                                                <c:if test="${empty info.tchlg5StarCdNm}">
                                                                    -
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <th>SQ정보</th>
                                                        <td>
                                                            <c:set var="item1" value="${rtnData.sqInfoList}" />
                                                            <c:set var="item2" value="${rtnData.sqInfoList1}" />
                                                            <c:set var="item3" value="${rtnData.sqInfoList2}" />
                                                            <c:choose>
                                                                <c:when test="${empty item1[0] and empty item1[1] and empty item1[2] and empty item1[3] and empty item2[0] and empty item2[1] and empty item2[2] and empty item2[3] and empty item3[0] and empty item3[1] and empty item3[2] and empty item3[3]}">
                                                                    <p class="f-body1">-</p>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:set var="count" value="1" />
                                                                    <c:choose>
                                                                        <c:when test="${empty item1[0] and empty item1[1] and empty item1[2] and empty item1[3]}">

                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <p class="f-body1">${count}. ${not empty item1[0] ? item1[0] : '-'} / ${not empty item1[1] ? item1[1] : '-'} / ${not empty item1[2] ? item1[2]+= ' 년' : '-'} / ${not empty item1[3] ? item1[3] : '-'}</p>
                                                                            <c:set var="count" value="${count + 1}" />
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${empty item2[0] and empty item2[1] and empty item2[2] and empty item2[3]}">

                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <p class="f-body1">${count}. ${not empty item2[0] ? item2[0] : '-'} / ${not empty item2[1] ? item2[1] : '-'} / ${not empty item2[2] ? item2[2]+= ' 년' : '-'} / ${not empty item2[3] ? item2[3] : '-'}</p>
                                                                            <c:set var="count" value="${count + 1}" />
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${empty item3[0] and empty item3[1] and empty item3[2] and empty item3[3]}">

                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <p class="f-body1">${count}. ${not empty item3[0] ? item3[0] : '-'} / ${not empty item3[1] ? item3[1] : '-'} / ${not empty item3[2] ? item3[2]+= ' 년' : '-'} / ${not empty item3[3] ? item3[3] : '-'}</p>
                                                                            <c:set var="count" value="${count + 1}" />
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">담당임원 정보</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>신청자 기본 정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>이름</th>
                                                <td>${picInfo.picName}</td>
                                            </tr>
                                            <tr>
                                                <th>휴대폰번호</th>
                                                <td>${picInfo.cmssrHpNo}</td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>${picInfo.picEmail}</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>${picInfo.cmssrCmpnTelNo}</td>
                                            </tr>
                                            <tr>
                                                <th>부서</th>
                                                <td>${picInfo.picDeptNm}</td>
                                            </tr>
                                            <tr>
                                                <th>직급</th>
                                                <td>${picInfo.picPstnNm}</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">사업신청 정보</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="data-view-form">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">부품사 규모</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.sizeCdNm}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">주고객사 납품비율</p>
                                            </div>
                                            <div class="td">
                                                <div class="dash-list-w">
                                                    <c:choose>
                                                        <c:when test="${not empty manageDto.dlvryCmpnList}">
                                                            <c:forEach var="dlvryCmpnList" items="${manageDto.dlvryCmpnList}" varStatus="status">
                                                                <div class="dash-list">
                                                                    <div class="item">
                                                                        <p class="item-title f-body2">업체명</p>
                                                                        <p class="item-txt f-head">${dlvryCmpnList.dlvryCmpnNm}</p>
                                                                    </div>
                                                                    <div class="item">
                                                                        <p class="item-title">비율</p>
                                                                        <p class="item-txt f-head">${dlvryCmpnList.dlvryRate}%</p>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">국내완성차 의존율</p>
                                            </div>
                                            <div class="td">
                                                <div class="dash-list-w">
                                                    <c:choose>
                                                        <c:when test="${not empty manageDto.dpndCmpnList}">
                                                            <c:forEach var="dpndCmpnList" items="${manageDto.dpndCmpnList}" varStatus="status">
                                                                <div class="dash-list">
                                                                    <div class="item">
                                                                        <p class="item-title f-body2">업체명</p>
                                                                        <p class="item-txt f-head">${dpndCmpnList.dpndnCmpnNm}</p>
                                                                    </div>
                                                                    <div class="item">
                                                                        <p class="item-title">비율</p>
                                                                        <p class="item-txt f-head">${dpndCmpnList.dpndnRate}%</p>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">해외 의존율</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.frgnDpndnRate} %</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">국내 매출액</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.dmstcSlsPmt} 억</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">해외 매출액</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.frgnSlsPmt} 억</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">전체 매출액</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.ttlSlsPmt} 억</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">자동차부품 매출액</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.carPartSlsPmt} 억</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">자동차부품 외 매출액</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.carPartXcludSlsPmt} 억</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">공장주소</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">(${manageDto.fctryZipcode}) ${manageDto.fctryBscAddr} ${manageDto.fctryDtlAddr}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">소재지역</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.firstRgnsCdNm} ${manageDto.scndRgnsCdNm}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">홈페이지 주소</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${not empty manageDto.hmpgeUrl? manageDto.hmpgeUrl : "-"}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">신청사유</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.appctnRsnCdNm}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">품질담당 인원</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.qltyPicCnt} 명</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">컨설팅요청 세부내용</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${manageDto.rqstCntn}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">회사소개서</p>
                                            </div>
                                            <div class="td">
                                                <c:choose>
                                                    <c:when test="${not empty rtnData.itrdcFileSeq}">
                                                        <div class="attatched-file-area">
                                                            <a class="btn-text-icon download-bg gray" href="/file/download?fileSeq=${rtnData.itrdcFileSeq}&fileOrd=${rtnData.itrdcFileOrd}" title="파일 다운로드" download><span>${rtnData.itrdcFileName}</span></a>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">개선활동 추진계획서</p>
                                            </div>
                                            <div class="td">
                                                <c:choose>
                                                    <c:when test="${not empty rtnData.impvmFileSeq}">
                                                        <div class="attatched-file-area">
                                                            <a class="btn-text-icon download-bg gray" href="/file/download?fileSeq=${rtnData.impvmFileSeq}&fileOrd=${rtnData.impvmFileOrd}" title="파일 다운로드" download><span>${rtnData.impvmFileName}</span></a>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <c:if test="${srvCnt > 0}"> <!-- 설문 응답 가능 기간에 포함되는 설문-->
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area survey" data-seq="${rtnData.cnstgSeq}">
                                    <p class="f-title3">컨설팅 만족도 설문조사 참여내역</p>
                                </div>
                                <div class="sec-con-area ${survey}" data-seq="${rtnData.cnstgSeq}">
                                    <c:forEach var="srvList" items="${rtnData.rsumeList}" varStatus="status">
                                        <div class="gray-bg-sec">
                                            <!-- 2024-02-23 a태그 감싸기 -->
                                            <a ${empty survey ? "" : 'href="javascript:"'}>
                                                <div class="con-list-box-w">
                                                    <div class="con-list-box">
                                                        <p class="f-head">컨설팅 만족도 설문</p>
                                                        <div class="ul-txt-w info">
                                                            <div class="ul-txt-list">
                                                                <div class="ul-txt">
                                                                    <dl><dt class="f-caption2">등록일시</dt>
                                                                        <dd class="f-caption1">${ kl:convertDate(srvList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</dd>
                                                                    </dl>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <c:if test="${rspnCnt != 1 }"> <!-- 설문 조사 참여 여부 -->
                                                    <div class="status-circle"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                        <p class="txt f-body1">미참여</p>
                                                    </div>
                                                </c:if>
                                                <c:if test="${rspnCnt == 1 }">
                                                    <div class="status-circle on"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                        <p class="txt f-body1">참여완료</p>
                                                    </div>
                                                </c:if>
                                            </a>
                                            <!-- // 2024-02-23 a태그 감싸기 -->
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion align-right">
                        <a class="btn-solid small black-bg" href="/my-page/consulting/list"><span>목록</span></a>
                    </div>
                </div>
            </div>
        </div>
        </div>