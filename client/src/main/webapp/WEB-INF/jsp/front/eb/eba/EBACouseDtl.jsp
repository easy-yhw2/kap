<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-notice.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-notice-mobile.jpg" alt="">
            </div>
        </div>
    </div>

    <div class="divide-con-area">
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="training-view-page">
                            <div class="training-list">
                                <div class="img-area">
                                    <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                </div>
                                <div class="txt-area">
                                    <div class="top-line">
                                        <div class="sort-label-area">
                                            <p class="label"><span>${rtnData.prntCdNm}</span></p>
                                            <p class="label"><span>${rtnData.ctgryCdNm}</span></p>
                                        </div>
                                        <p class="training-name f-title1">${rtnData.nm}</p>
                                        <p class="training-explain-txt">${rtnData.smmryNm}</p>
                                    </div>
                                    <div class="class-property-w">
                                        <div class="property-list offline"><!-- offline: 집체교육 -->
                                            <p class="txt">
                                                <span>${rtnData.stduyMthdCdNm}</span>
                                            </p>
                                        </div>
                                        <div class="property-list time"><!-- time: 학습시간 -->
                                            <p class="txt">
                                                <span>${rtnData.stduyDdCdNm}일(${rtnData.stduyTimeCdNm}시간)</span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec anchor-con scroll-motion" id="roundSection">
                    <div class="for-motion">
                        <div class="sec-tit-area non-block"><!-- non-block : pc/mobile 둘다 flex -->
                            <p class="f-title3">회차정보</p>
                            <p class="article-total-count f-body2">총 <span>15</span>개</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="index-list-w">
                                <div class="list-item available accepting"><!-- available: 신청 가능한 회차 --><!-- accepting: 접수중 -->
                                    <p class="available-label">
                                        <span>신청 가능한 회차</span>
                                    </p>
                                    <div class="cont">
                                        <div class="top-area">
                                            <div class="left">
                                                <div class="group">
                                                    <p class="index-num f-title3">8회차</p>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>사출</span></p>
                                                        <p class="box-label bigger accepting"><span>접수중</span></p>
                                                        <!--
                                                          접수중: accepting
                                                          접수대기: waiting
                                                          접수마감: end
                                                        -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="cont-area">
                                            <div class="btn-wrap">
                                                <button class="btn-text-icon black-arrow" type="button"><span>회차 담당자 문의</span></button>
                                                <button class="btn-text-icon black-arrow" type="button"><span>온라인 강의목차</span></button>
                                            </div>
                                            <div class="info-list-w">
                                                <div class="info-list">
                                                    <p class="tit f-caption2">강사</p>
                                                    <p class="txt f-body2">홍길동</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">정원</p>
                                                    <p class="txt f-body2">30명(모집 후 선발)</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육장소</p>
                                                    <p class="txt f-body1"><a href="javascript:" title="교육장 안내 팝업 열기">글로벌상생협력센터(GPC)(경주)</a></p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">접수기간</p>
                                                    <p class="txt f-body2">2023.08.01 13:00 ~ 2023.08.31 14:00</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육기간</p>
                                                    <p class="txt f-body2">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</p>
                                                </div>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-set">
                                                    <a class="btn-text-icon download" href="javascript:" download=""><span>안내문</span></a>
                                                </div>
                                                <div class="btn-set">
                                                    <a class="btn-solid small black-bg" href="javascript:"><span>신청하기</span></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="list-item">
                                    <div class="cont">
                                        <div class="top-area">
                                            <div class="left">
                                                <div class="group">
                                                    <p class="index-num f-title3">9회차</p>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>사출</span></p>
                                                        <p class="box-label bigger waiting"><span>접수대기</span></p>
                                                        <!--
                                                          접수중: accepting
                                                          접수대기: waiting
                                                          접수마감: end
                                                        -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="cont-area">
                                            <div class="btn-wrap">
                                                <button class="btn-text-icon black-arrow" type="button"><span>회차 담당자 문의</span></button>
                                                <button class="btn-text-icon black-arrow" type="button"><span>온라인 강의목차</span></button>
                                            </div>
                                            <div class="info-list-w">
                                                <div class="info-list">
                                                    <p class="tit f-caption2">강사</p>
                                                    <p class="txt f-body2">홍길동</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">정원</p>
                                                    <p class="txt f-body2">30명(모집 후 선발)</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육장소</p>
                                                    <p class="txt f-body2"><a href="javascript:" title="교육장 안내 팝업">글로벌상생협력센터(GPC)(경주)</a></p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">접수기간</p>
                                                    <p class="txt f-body2">2023.08.01 13:00 ~ 2023.08.31 14:00</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육기간</p>
                                                    <p class="txt f-body2">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</p>
                                                </div>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-set">
                                                    <a class="btn-text-icon download" href="javascript:" download=""><span>안내문</span></a>
                                                </div>
                                                <div class="btn-set">
                                                    <a class="btn-solid small black-bg disabled" href="javascript:"><span>접수대기</span></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="list-item">
                                    <div class="cont">
                                        <div class="top-area">
                                            <div class="left">
                                                <div class="group">
                                                    <p class="index-num f-title3">7회차</p>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>사출</span></p>
                                                        <p class="box-label bigger end"><span>마감</span></p>
                                                        <!--
                                                          접수중: accepting
                                                          접수대기: waiting
                                                          접수마감: end
                                                        -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="cont-area">
                                            <div class="btn-wrap">
                                                <button class="btn-text-icon black-arrow" type="button"><span>회차 담당자 문의</span></button>
                                                <button class="btn-text-icon black-arrow" type="button"><span>온라인 강의목차</span></button>
                                            </div>
                                            <div class="info-list-w">
                                                <div class="info-list">
                                                    <p class="tit f-caption2">강사</p>
                                                    <p class="txt f-body2">홍길동</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">정원</p>
                                                    <p class="txt f-body2">30명(모집 후 선발)</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육장소</p>
                                                    <p class="txt f-body2"><a href="javascript:" title="교육장 안내 팝업">글로벌상생협력센터(GPC)(경주)</a></p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">접수기간</p>
                                                    <p class="txt f-body2">2023.08.01 13:00 ~ 2023.08.31 14:00</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육기간</p>
                                                    <p class="txt f-body2">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</p>
                                                </div>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-set">
                                                    <a class="btn-text-icon download" href="javascript:" download=""><span>안내문</span></a>
                                                </div>
                                                <div class="btn-set">
                                                    <a class="btn-solid small black-bg disabled" href="javascript:"><span>마감</span></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="btn-wrap add-load align-center">
                                <a class="btn-solid small black-line" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec anchor-con scroll-motion" id="outlineSection">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">과정소개</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                ${rtnData.itrdcCntn}
                                <%--<div class="paragraph">
                                    <p class="f-sub-head">품질 전문가로서의 첫 시작을 위한 교육!</p>
                                </div>
                                <div class="paragraph">
                                    <p class="f-sub-head">품질의 기본 개념부터 올바른 품질 문제 파악 및 실용적인 QC 7도구 활용 방법까지, 품질 세계에 입문한 부품사 직원들이 품질을 제대로 이해하고 업무에 바로 활용할 수 있도록 최고 품질 전문가의 실전 강의를 제공합니다.</p>
                                </div>--%>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec anchor-con scroll-motion" id="goalSection">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">학습목표</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                ${rtnData.stduyTrgtCntn}
                                <%--<div class="ul-txt-w highlight">
                                    <div class="ul-txt-list">
                                        <p class="ul-txt has-dot">(K) 품질이 중요한 이유에 대해 설명할 수 있다.</p>
                                        <p class="ul-txt has-dot">(K) 완성차 구매/품질 정책을 조직 시스템 및 업무에 반영한다.</p>
                                        <p class="ul-txt has-dot">(S) 품질 문제를 정의하고 해결 방안을 제시할 수 있다.</p>
                                        <p class="ul-txt has-dot">(S) 현장에서 품질 문제 해결을 위해 QC-7 Tool를 용도에 맞게 활용할 수 있다.</p>
                                        <p class="ul-txt has-dot">(S) 현장에서 품질 개선을 위한 기법을 적용할 수 있다.</p>
                                    </div>
                                </div>--%>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">학습대상</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="table-sec">
                                <div class="table-box need-scroll">
                                    <%--<table class="basic-table">
                                        <caption>신청자 기본 정보</caption>
                                        <colgroup>
                                            <col style="width: 273rem;">
                                            <col style="width: 820rem;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>회사</th>
                                            <td>1차 부품사</td>
                                        </tr>
                                        <tr>
                                            <th>업종</th>
                                            <td>구분없음</td>
                                        </tr>
                                        <tr>
                                            <th>직무</th>
                                            <td>구분없음</td>
                                        </tr>
                                        <tr>
                                            <th>직급</th>
                                            <td>사원/대리(매니저, 주임)</td>
                                        </tr>
                                        <tr>
                                            <th>기타</th>
                                            <td>경력 3년 이상 필수</td>
                                        </tr>
                                        </tbody>
                                    </table>--%>

                                        <table class="basic-table">
                                            <caption>신청자 기본 정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>

                                                <c:forEach var="list" items="${edTarget}">
                                                    <c:set var="tempNm" value="" />
                                                    <tr>
                                                    <c:forEach var="targetList" items="${list.edList}">
                                                        <c:choose>
                                                            <c:when test="${targetList.dpth eq '2'}">
                                                                <th>${targetList.cdNm}</th>

                                                                <c:if test="${targetList.cdNm ne '기타' && targetList.cd ne 'ED_TARGET05001'}">
                                                                    <c:forEach var="rtnTrgtDataList" items="${rtnTrgtData}">
                                                                        <c:if test="${rtnTrgtDataList.targetCd eq targetList.cd}">
                                                                            <c:set var="tempNm" value="${rtnTrgtDataList.targetCdNm}" />
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:if>

                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>
                                                                    ${tempNm}
                                                                    <%--<c:if test="${targetList.cdNm ne '기타' && targetList.cd ne 'ED_TARGET05001'}">
                                                                        <c:forEach var="rtnTrgtDataList" items="${rtnTrgtData}">
                                                                            <c:if test="${rtnTrgtDataList.targetCd eq targetList.cd}">
                                                                                ${rtnTrgtDataList.targetCdNm}
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </c:if>--%>
                                                                </td>
                                                                <c:if test="${targetList.cdNm eq '기타' && targetList.cd eq 'ED_TARGET05001'}">
                                                                    <td>
                                                                        <c:if test="${empty rtnTrgtData[rtnTrgtData.size()-1].etcNm}">
                                                                            없음
                                                                        </c:if>
                                                                        <c:if test="${not empty rtnTrgtData[rtnTrgtData.size()-1].etcNm}">
                                                                            ${rtnTrgtData[rtnTrgtData.size()-1].etcNm}
                                                                        </c:if>
                                                                    </td>
                                                                </c:if>

                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">학습 기본정보</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="table-sec">
                                <div class="table-box need-scroll">
                                    <table class="basic-table">
                                        <caption>신청자 기본 정보</caption>
                                        <colgroup>
                                            <col style="width: 273rem;">
                                            <col style="width: 820rem;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>학습방식</th>
                                            <td>${rtnData.stduyMthdCdNm}</td>
                                        </tr>
                                        <tr>
                                            <th>수료기준</th>
                                            <td>출석 ${rtnData.cmptnStndCdNm}% 이상, 평가 ${rtnData.cmptnJdgmtCdNm}점 이상</td>
                                        </tr>
                                        <tr>
                                            <th>학습시간</th>
                                            <td>${rtnData.stduyDdCdNm}일 / ${rtnData.stduyTimeCdNm}시간</td>
                                        </tr>
                                        <tr>
                                            <th>학습 준비물</th>
                                            <td>${rtnData.stduySuplsNm}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">학습내용</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="curriculum-div">
                                ${rtnData.pcStduyCntn}
                                ${rtnData.mblStduyCntn}
                                <%--<div class="day-list">
                                    <div class="tit-area">
                                        <p class="tit f-title2">1일차</p>
                                    </div>
                                    <div class="con-area">
                                        <div class="chapter-list">
                                            <p class="chapter-tit f-head">품질 경쟁력 확보의 중요성</p>
                                            <div class="ul-txt-w highlight">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt has-dot">품질의 정의</p>
                                                    <p class="ul-txt has-dot">품질의 진화</p>
                                                    <p class="ul-txt has-dot">협력사 품질 평가 제도</p>
                                                </div>
                                            </div>
                                            <p class="method">강의 / 퀴즈</p>
                                        </div>
                                        <div class="chapter-list">
                                            <p class="chapter-tit f-head">품질 문제의 인식과 해결</p>
                                            <div class="ul-txt-w highlight">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt has-dot">문제의 인식과 해결</p>
                                                    <p class="ul-txt has-dot">품질 문제를 통계적으로 표현하기</p>
                                                    <p class="ul-txt has-dot">품질 문제의 해결(SW2H)</p>
                                                </div>
                                            </div>
                                            <p class="method">강의 / 실습</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="day-list">
                                    <div class="tit-area">
                                        <p class="tit f-title2">2일차</p>
                                    </div>
                                    <div class="con-area">
                                        <div class="chapter-list">
                                            <p class="chapter-tit f-head">품질 경쟁력 확보의 중요성</p>
                                            <div class="ul-txt-w highlight">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt has-dot">문제해결 절차와 QC7 도구 활용 흐름</p>
                                                    <p class="ul-txt has-dot">QC7 도구 별 활용 실습</p>
                                                </div>
                                            </div>
                                            <p class="method">강의 / 실습</p>
                                        </div>
                                        <div class="chapter-list">
                                            <p class="chapter-tit f-head">품질 문제의 인식과 해결</p>
                                            <div class="ul-txt-w highlight">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt has-dot">7Step, 8D report, 품질분임조 등 품질 향상 기업 활용 사례</p>
                                                </div>
                                            </div>
                                            <p class="method">강의 / 토론</p>
                                        </div>
                                    </div>
                                </div>--%>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec anchor-con scroll-motion" id="connectedSection">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">연계학습</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="training-swiper-area swiper-role">
                                <div class="inner-title">
                                    <p class="f-title2">선수과정(2)</p>
                                </div>
                                <div class="swiper-container training-swiper">
                                    <div class="swiper-wrapper marquee_wrapper1">
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>1줄말줄임테스트1줄말줄임테스트1줄말줄임테스트1줄말줄임테스트1줄말줄임테스트1줄말줄임테스트1줄말줄임테스트</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 waiting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger waiting"><span>접수대기</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수대기</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 waiting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <!-- <img src="/common/images/img-main-training-offline-01.jpg" alt=""> -->
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger end"><span>접수마감</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수마감</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 end" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger end"><span>접수마감</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수마감</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <div class="swiper-button-next circle-arr btn_next"></div>
                                <div class="swiper-button-prev circle-arr btn_prev"></div>
                            </div>
                            <div class="training-swiper-area swiper-role">
                                <div class="inner-title">
                                    <p class="f-title2">후속과정(4)</p>
                                </div>
                                <div class="swiper-container training-swiper">
                                    <div class="swiper-wrapper marquee_wrapper1">
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <!-- <img src="/common/images/img-main-training-offline-01.jpg" alt=""> -->
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 waiting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <!-- <img src="/common/images/img-main-training-offline-01.jpg" alt=""> -->
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">글로벌상생교육센터(GPC)(경주)</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger waiting"><span>접수대기</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수대기</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 waiting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <!-- <img src="/common/images/img-main-training-offline-01.jpg" alt=""> -->
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">글로벌상생교육센터(GPC)(경주)</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger end"><span>접수마감</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수마감</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 end" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">글로벌상생교육센터(GPC)(경주)</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger end"><span>접수마감</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수마감</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <div class="swiper-button-next circle-arr btn_next"></div>
                                <div class="swiper-button-prev circle-arr btn_prev"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="page-bot-btn-sec scroll-motion">
                <div class="btn-wrap align-center for-motion">
                    <a class="btn-solid small black-bg" href="/education/apply/list"><span>목록</span></a>
                </div>
            </div>

            <!-- 접수중 하단 플로팅 영역 -->
            <div class="accepting-fixed-area">
                <div class="for-position">
                    <button class="open-click-area" type="button">
                        <p class="tit">8회차 <span class="status">접수중</span></p>
                        <div class="btn-text-icon plus"><span>더보기</span></div>
                    </button>

                    <div class="hide-area">
                        <div class="inner-con">
                            <div class="tit-area">
                                <p class="f-title1">2023 1차 사업재편지원 사업  (상시접수중)</p>
                            </div>
                            <div class="con-area">
                                <div class="scroll-area">
                                    <div class="info-line-list-w">
                                        <div class="list">
                                            <p class="tit">접수일자</p>
                                            <p class="txt">2023.02.01 10:00 ~ 2023.02.01 17:00</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">교육일자</p>
                                            <p class="txt">2023.02.01 10:00 - 2023.02.01 17:00</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">강사</p>
                                            <p class="txt">
                                                <!-- <span class="img"><img src="/common/images/img-instructor-profile-01.jpg" alt=""></span> -->
                                                <span>홍길동</span>
                                            </p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">정원</p>
                                            <p class="txt">30명</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">교육장소</p>
                                            <p class="txt">경주GPC
                                            </p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">학습방식</p>
                                            <p class="txt">집체교육</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">학습방식</p>
                                            <p class="txt">집체교육</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">학습방식</p>
                                            <p class="txt">집체교육</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">학습방식</p>
                                            <p class="txt">집체교육</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">학습방식</p>
                                            <p class="txt">집체교육</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">학습방식</p>
                                            <p class="txt">집체교육</p>
                                        </div>
                                        <div class="list">
                                            <p class="tit">학습방식</p>
                                            <p class="txt">집체교육</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="btn-wrap">
                                <div class="btn-set">
                                    <a class="btn-solid small gray-bg has-icon tel" href="javascript:" title="회차 담당자 문의하기"><span>회차 담당자 문의</span></a>
                                    <a class="btn-solid small gray-bg has-icon lecture" href="javascript:" title="온라인 강의목차 보기"><span>온라인 강의목차</span></a>
                                    <a class="btn-solid small gray-bg has-icon download" href="javascript:" download title="안내문 다운로드"><span>안내문 다운로드</span></a>
                                </div>
                                <div class="btn-set">
                                    <a class="btn-solid small black-bg" href="javascript:"><span>신청하기</span></a>
                                </div>
                            </div>

                            <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 앵커 영역 -->
            <div class="anchor-btn-w">
                <button class="anchor-btn active" href="javascript:">
                    <span>회차정보</span>
                </button>
                <button class="anchor-btn" href="javascript:">
                    <span>과정소개</span>
                </button>
                <button class="anchor-btn" href="javascript:">
                    <span>학습정보</span>
                </button>
                <button class="anchor-btn" href="javascript:">
                    <span>연계학습</span>
                </button>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->