<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/bd/bdc/BDCFaqListCtrl">
    <form class="form-horizontal" id="frmData" name="frmData" method="get">
        <!-- 상세로 이동시 시퀀스 -->
        <input type="hidden" id="detailsKey" name="detailsKey" value="" />
        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <div class="sub-top-vis-area basic-page">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">FAQ</span></p>
                </div>
                <div class="img-area">
                    <div class="img">
                        <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-faq.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-faq-mobile.jpg" alt="">
                    </div>
                </div>
            </div>

            <div class="divide-con-area">
                <div class="lnb-area">
                    <div class="for-motion" style="margin-top: 0; opacity:1;">
                        <div class="lnb-list">
                            <a class="btn-two-depth" href="javascript:"><span>재단소개</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                            <div class="three-depth-wrap">
                                <a class="btn-three-depth" href="javascript:"><span>인사말</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>재단개요</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>재단연혁</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>재단조직</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>윤리경영</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>경영공시</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>홍보자료</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>오시는길</span></a>
                            </div>
                        </div>
                        <div class="lnb-list">
                            <a class="btn-two-depth active" href="javascript:"><span>고객센터</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                            <div class="three-depth-wrap">
                                <a class="btn-three-depth" href="javascript:"><span>공지사항</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>재단소식</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>뉴스레터 구독</span></a>
                                <a class="btn-three-depth active" href="javascript:"><span>FAQ</span></a>
                                <a class="btn-three-depth" href="javascript:"><span>1:1 문의</span></a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="right-con-area">
                    <div class="tab-btn-area scroll-motion">
                        <div class="txt-tab-swiper for-motion">
                            <div class="swiper-container">
                                <div class="swiper-wrapper">
                                    <a class="swiper-slide txt-tab-btn active" id="ctgryCd" href="javascript:">
                                        <p class="txt"><span class="menu-name">전체</span></p>
                                    </a>
                                    <c:forEach var="cdList" items="${cdDtlList.BOARD_TYPE_CD}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'FAQ0')}">
                                            <a class="swiper-slide txt-tab-btn <c:if test="${rtnData.ctgryCd eq cdList.cd}">active</c:if>" id="ctgryCd" href="./list?ctgryCd=${cdList.cd}">
                                                <p class="txt"><span class="menu-name" data-ctgry-cd="${cdList.cd}">${cdList.cdNm}</span></p>
                                            </a>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="info-head">
                                    <p class="article-total-count f-body2">총 <span>${rtnData.totalCount}</span>건</p>
                                    <div class="form-input srch-input">
                                        <input type="text" id="srchVal" name="srchVal" value="${rtnData.srchVal}" placeholder="검색어를 입력해 주세요.">
                                        <div class="input-btn-wrap">
                                            <button class="delete-btn" title="지우기" type="button"></button>
                                            <button class="srch-btn" id="searchBtn" title="검색"></button>
                                        </div>
                                    </div>
                                </div>

                                <div class="board-list">
                                    <div class="article-list-w accordion-st" id="listContainer">
                                    </div>
                                </div>

                                <div class="btn-wrap add-load align-center">
                                    <a class="btn-solid small black-line" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>