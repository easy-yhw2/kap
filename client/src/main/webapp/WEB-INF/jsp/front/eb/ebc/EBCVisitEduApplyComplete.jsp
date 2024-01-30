<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<script>
    window.onpageshow = function(event) {
        if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
            // 이벤트 추가하는 곳
            alert("정상적인 접근이 아닙니다.");
            location.href="/";

        }
    }
</script>
<div id="wrap" data-controller="controller/eb/ebc/EBCVisitEduCtrl">
    <div class="cont-wrap">
        <div class="sub-top-vis-area apply-page consult-biz">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">방문교육신청</span></p>
                <div class="apply-step-w">
                    <div class="for-move">
                        <div class="step-list completed">
                            <p class="step-num">1</p>
                            <p class="step-con">기본정보</p>
                        </div>
                        <div class="step-list completed">
                            <p class="step-num">2</p>
                            <p class="step-con">정보입력</p>
                        </div>
                        <div class="step-list ongoing">
                            <p class="step-num">3</p>
                            <p class="step-con">신청완료</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="img-area">
                <div class="gray-bg"></div>
                <div class="graphic-item-w">
                    <div class="item"></div>
                    <div class="item"></div>
                </div>
            </div>
        </div>

        <div class="divide-con-area">
            <!--LNB 시작-->
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
            <!--LNB 끝-->

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="status-con-box gray-bg completed">
                                <div class="cont-for-padding">
                                    <p class="f-title1"><span class="color-sky">방문교육</span><br> 신청이 완료되었습니다.</p>
                                    <div class="def-list-w">
                                        <div class="def-list">
                                            <p class="tit f-head">신청일시</p>
                                            <p class="txt f-sub-head">${kl:convertDate(rtnData.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="guide-info-area scroll-motion">
                                <div class="for-motion">
                                    <div class="divide-box">
                                        <p class="exclamation-txt f-body1">교육 신청 후에는 수정이 불가능하며, 교육 신청 취소 후 다시 접수해야 합니다.</p>
                                        <p class="exclamation-txt f-body1">교육 신청 취소는 마이페이지 &gt; 교육/세미나 신청내역에서 가능합니다.</p>
                                    </div>
                                    <div class="divide-box"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap align-center for-motion">
                        <div class="btn-set">
                            <a class="btn-solid small black-bg" href="javascript:"><span>신청내역 보기</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
