<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>KAP</title>
	<link rel="shortcut icon" href="/common/images/favicon.ico" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta name="keywords" content="KAP"/>
	<meta name="description" content="KAP"/>
	<meta property="og:site_name" content="KAP" id="og-sitename-value"/>
	<meta property="og:type" content="website" id="og-type-value"/>
	<meta property="og:url" content="URL" id="og-url-value"/>
	<meta property="og:title" content="NOTICE | KAP" id="og-title-value"/>
	<meta property="og:description" content="KAP 홈페이지에 방문하신 것을 환영합니다." id="og-description-value"/>
	<meta property="og:image" content="img URL" id="og-image-value"/>
	<link rel="stylesheet" href="/common/css/swiper.css" />
	<link rel="stylesheet" href="/common/css/fonts.css" />
	<link rel="stylesheet" href="/common/css/common.css" />
	<link rel="stylesheet" href="/common/css/style.css" />
	<script type="text/javascript" src="/common/js/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="/common/js/gsap.min.js"></script>
	<script type="text/javascript" src="/common/js/ScrollTrigger.min.js"></script>
	<script type="text/javascript" src="/common/js/swiper.min.js"></script>
	<script type="text/javascript" src="/common/js/script.js"></script>
</head>
<body>
<div id="wrap">
	<header id="header">
		<!-- header - START -->
		<h1><a class="logo" href="#"></a></h1>
		<a href="javascript:" class="prev-btn icon-btn"></a>
		<h2 class="nav-tit">${ not empty pageMenuDto ? pageMenuDto.menuNm : ''}</h2><!-- @ 해당 서브페이지의 이름 -->
		<nav>
			<ul class="gnb">
				<c:if test="${ not empty gnbMenuList}">
					<ul class="gnb">
						<c:set var="nextDpthIndex" value="" />
						<c:forEach var="menu" items="${gnbMenuList}" varStatus="status">
							<c:choose>
								<c:when test="${ status.first or menu.dpth eq 3}">
									<li>
										<a class="one-depth" href="javascript:">${ menu.menuNm }</a>
								</c:when>
								<c:when test="${ menu.dpth eq 4}">
									<li><a class="two_depth ${ menu.wnppYn eq 'Y' ? 'menu_employ' : ''  }" href="${ empty menu.userUrl ? 'javascript:' : menu.userUrl  }" target="${ menu.wnppYn eq 'Y' ? '_blank' : '_self'  }" <c:if test="${menu.wnppYn eq 'Y'}">rel="noopener noreferrer"</c:if> >${ menu.menuNm }</a></li>
								</c:when>
							</c:choose>
							<c:set var="nextDpthIndex" value="${ status.count }" />
							<c:if test="${ status.last }">
								<c:set var="nextDpthIndex" value="0" />
							</c:if>
							<c:if test="${ status.last or gnbMenuList[nextDpthIndex].dpth eq 3 }">
								</ul>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
				<li>
					<a class="one-depth" href="javascript:">상생사업</a>
					<div class="two-pack-wrap">
						<ul class="two-pack">
							<li>
								<a class="two-depth" href="javascript:">경쟁력향상지원</a>
								<ul class="three-pack">
									<li><a class="three-depth" href="javascript:">미래차다각화</a></li>
									<li><a class="three-depth" href="javascript:">사업재편지원</a></li>
									<li><a class="three-depth" href="javascript:">보안솔루션구축</a></li>
									<li><a class="three-depth" href="javascript:">안전설비구축</a></li>
									<li><a class="three-depth" href="javascript:">탄소배출저감</a></li>
									<li><a class="three-depth" href="javascript:">스마트공장</a></li>
								</ul>
							</li>
							<li>
								<a class="two-depth" href="javascript:">자금지원</a>
								<ul class="three-pack">
									<li><a class="three-depth" href="javascript:">시험계측장비</a></li>
									<li><a class="three-depth" href="javascript:">검교정</a></li>
									<li><a class="three-depth" href="javascript:">공급망안정화기금</a></li>
								</ul>
							</li>
							<li>
								<a class="two-depth" href="javascript:">포상지원</a>
								<ul class="three-pack">
									<li><a class="three-depth" href="javascript:">자동차부품산업대상</a></li>
									<li><a class="three-depth" href="javascript:">미래차공모전</a></li>
								</ul>
							</li>
							<li>
								<a class="two-depth" href="javascript:">설문조사</a>
								<ul class="three-pack">
									<li><a class="three-depth" href="javascript:">상생협력체감도조사</a></li>
								</ul>
							</li>
						</ul>
					</div>
				</li>
				<li>
					<a class="one-depth" href="javascript:">재단정보</a>
					<div class="two-pack-wrap">
						<ul  class="two-pack">
							<li>
								<a class="two-depth" href="javascript:">재단소개</a>
								<ul class="three-pack">
									<li><a class="three-depth" href="javascript:">인사말</a></li>
									<li><a class="three-depth" href="javascript:">재단개요</a></li>
									<li><a class="three-depth" href="javascript:">재단연혁</a></li>
									<li><a class="three-depth" href="javascript:">재단조직</a></li>
									<li><a class="three-depth" href="javascript:">윤리경영</a></li>
									<li><a class="three-depth" href="javascript:">경영공시</a></li>
									<li><a class="three-depth" href="javascript:">홍보자료</a></li>
									<li><a class="three-depth" href="javascript:">오시는길</a></li>
								</ul>
							</li>
							<li>
								<a class="two-depth" href="javascript:">고객센터</a>
								<ul class="three-pack">
									<li><a class="three-depth" href="javascript:">공지사항</a></li>
									<li><a class="three-depth" href="javascript:">재단소식</a></li>
									<li><a class="three-depth" href="javascript:">뉴스레터</a></li>
									<li><a class="three-depth" href="javascript:">FAQ</a></li>
									<li><a class="three-depth" href="javascript:">1:1문의</a></li>
								</ul>
							</li>
						</ul>
					</div>
				</li>
			</ul>
			<div class="line"></div>
		</nav>

		<div class="util-area">
			<div class="pop-wrap">
				<button class="on noti-btn icon-btn" type="button" title="팝업"><span>공지</span></button><!-- @ on 클래스 추가하면, 알림창 우측상단에 표시 -->
				<div class="pop-box">
					<p class="f-head">공지</p>
					<div class="list">
						<a href="javascript:" title="링크 이동">
							<!--
                              @ N아이콘 사용 시, <span class="new-icon">
                                작은 아이콘은 small 클래스 추가
                            -->
							<p class="tit f-body2"><span class="new-icon small" aria-label="새로운 정보"></span>협력사 온라인 정보공유 소통 플랫폼 오픈 공지</p>
							<span class="date f-caption2">2023.09.18</span>
						</a>
						<a href="javascript:" title="링크 이동">
							<p class="tit f-body2"><span class="new-icon small" aria-label="새로운 정보"></span>대구시, 자동차부품산업진흥재단 등과 미래자동차 산업 육성 협력</p>
							<span class="date f-caption2">2023.09.18</span>
						</a>
						<a href="javascript:" title="링크 이동">
							<p class="tit f-body2">2023년 하반기 자문위원 채용 공고</p>
							<span class="date f-caption2">2023.09.18</span>
						</a>
					</div>
				</div>
			</div>
			<button class="search-btn icon-btn" type="button" title=""><span>검색</span></button>
			<div class="pop-wrap">
				<button class="log-btn icon-btn" type="button" title="팝업"><span>로그인</span></button>
				<div class="pop-box">
					<div class="btn-wrap">
						<a class="btn-text-icon black-arrow" href="javascript:" title="링크 이동"><span>로그인</span></a>
					</div>
					<div class="btn-wrap">
						<a class="btn-text-icon black-arrow" href="javascript:" title="링크 이동"><span>회원가입</span></a>
					</div>
				</div>
			</div>
			<button class="init menu-btn icon-btn" type="button" title="">
				<span>메뉴</span>
				<i>
					<span class="top line"></span>
					<span class="mid line"></span>
					<span class="bot line"></span>
				</i>
			</button>
		</div>

		<section class="all-menu">
			<div class="scroll-area">
				<div class="menu-bg"></div>
				<div class="menu-wrap">
					<div class="menu-head">
						<p class="menu-tit f-xlarge-title">전체 서비스</p>
						<div class="log-menu">
							<!-- <p class="log-tit f-title3">MY</p> --><!-- @ 로그인 전에 보이는 요소 -->
							<p class="user-tit f-title1"><span>강문주</span>님 <br/>안녕하세요.</p><!-- @ 로그인 후에 보이는 요소 -->
							<div class="pc btn-wrap">
								<a class="btn-text-icon black-arrow" href="javascript:" title="링크 이동"><span>정보수정</span></a>
								<a class="btn-text-icon black-arrow" href="javascript:" title="링크 이동"><span>로그아웃</span></a>
							</div>
						</div>
						<!-- @ 로그인 후에 보이는 요소 -->
						<div class="loginfo-wrap">
							<div class="loginfo-box">
								<p class="info-tit f-title3">신청내역</p>
								<div class="info-cont">
									<ul class="counts">
										<li class="count">
											<span class="f-sub-head">교육사업</span>
											<a class="f-title1" href="javascript:">5</a>
										</li>
										<li class="count">
											<span class="f-sub-head">컨설팅사업</span>
											<a class="f-title1" href="javascript:">21</a>
										</li>
										<li class="count">
											<span class="f-sub-head">상생사업</span>
											<a class="f-title1" href="javascript:">0</a>
										</li>
									</ul>
									<div class="pc btn-wrap">
										<div class="btn-set">
											<a class="btn-solid small white-bg" href="javascript:"><span>증명서 발급</span></a>
											<a class="btn-solid small white-bg" href="javascript:"><span>1:1 문의</span></a>
										</div>
									</div>
								</div>
							</div>
							<div class="mob btn-wrap">
								<div class="btn-set">
									<a class="btn-solid small gray-bg" href="javascript:"><span>증명서 발급</span></a>
									<a class="btn-solid small gray-bg" href="javascript:"><span>1:1 문의</span></a>
								</div>
							</div>
							<p class="last-date f-caption2"><span>최근접속일</span><span class="date">2023.01.01 10:00</span></p>
						</div>
						<!-- // @ 로그인 후에 보이는 요소 -->
					</div>
					<ul class="gnb">
						<li>
							<div class="one-pack">
								<a class="one-depth for-move" href="javascript:">교육사업</a>
							</div>
							<ul class="two-pack">
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">교육사업</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">품질아카데미</a></li>
											<li><a class="three-depth" href="javascript:">제조/경영혁신</a></li>
											<li><a class="three-depth" href="javascript:">세미나</a></li>
										</ul>
									</div>
								</li>
							</ul>
						</li>
						<li>
							<div class="one-pack">
								<a class="one-depth for-move" href="javascript:">컨설팅사업</a>
							</div>
							<ul class="two-pack">
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">기술지도</a>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">경영컨설팅</a>
									</div>
								</li>
							</ul>
						</li>
						<li>
							<div class="one-pack">
								<a class="one-depth for-move" href="javascript:">상생사업</a>
							</div>
							<ul class="two-pack">
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">경쟁력향상지원</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">미래차다각화</a></li>
											<li><a class="three-depth" href="javascript:">사업재편지원</a></li>
											<li><a class="three-depth" href="javascript:">보안솔루션구축</a></li>
											<li><a class="three-depth" href="javascript:">안전설비구축</a></li>
											<li><a class="three-depth" href="javascript:">탄소배출저감</a></li>
											<li><a class="three-depth" href="javascript:">스마트공장</a></li>
										</ul>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">자금지원</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">시험계측장비</a></li>
											<li><a class="three-depth" href="javascript:">검교정</a></li>
											<li><a class="three-depth" href="javascript:">공급망안정화기금</a></li>
										</ul>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">포상지원</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">자동차부품산업대상</a></li>
											<li><a class="three-depth" href="javascript:">미래차공모전</a></li>
										</ul>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">설문조사</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">상생협력체감도조사</a></li>
										</ul>
									</div>
								</li>
							</ul>
						</li>
						<li>
							<div class="one-pack">
								<a class="one-depth for-move" href="javascript:">재단정보</a>
							</div>
							<ul class="two-pack">
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">재단소개</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">인사말</a></li>
											<li><a class="three-depth" href="javascript:">재단개요</a></li>
											<li><a class="three-depth" href="javascript:">재단연혁</a></li>
											<li><a class="three-depth" href="javascript:">재단조직</a></li>
											<li><a class="three-depth" href="javascript:">윤리경영</a></li>
											<li><a class="three-depth" href="javascript:">경영공시</a></li>
											<li><a class="three-depth" href="javascript:">홍보자료</a></li>
											<li><a class="three-depth" href="javascript:">오시는길</a></li>
										</ul>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">고객센터</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">공지사항</a></li>
											<li><a class="three-depth" href="javascript:">재단소식</a></li>
											<li><a class="three-depth" href="javascript:">뉴스레터</a></li>
											<li><a class="three-depth" href="javascript:">FAQ</a></li>
											<li><a class="three-depth" href="javascript:">1:1문의</a></li>
										</ul>
									</div>
								</li>
							</ul>
						</li>
					</ul>
					<div class="notice-wrap">
						<div class="notice-rolling">
							<ul>
								<li class="current"><a class="f-body2" href="javascript:"><span class="new-icon small" aria-label="새로운 정보"></span>협력사 온라인 정보공유 소통 플랫폼 오픈 공지 협력사 온라인 정보공유 소통 플랫폼 오픈 공지</a></li>
								<li class="next"><a class="f-body2" href="javascript:"><span class="new-icon small" aria-label="새로운 정보"></span>대구시, 자동차부품산업진흥재단 등과 미래자동차 산업 육성 협력 대구시, 자동차부품산업진흥재단 등과 미래자동차 산업 육성 협력</a></li>
								<li class="prev"><a class="f-body2" href="javascript:">2023년 하반기 자문위원 채용 공고 2023년 하반기 자문위원 채용 공고</a></li>
							</ul>
						</div>
					</div>

				</div>
			</div>
		</section>

		<section class="all-srch">
			<div class="srch-wrap">
				<div class="form-input srch-input">
					<input type="text" placeholder="검색어를 입력해 주세요.">
					<div class="input-btn-wrap">
						<button class="delete-btn" title="지우기" type="button"></button>
						<button class="srch-btn" title="검색"></button>
					</div>
				</div>
				<ul class="top-srchs">
					<li class="srch-item">
						<a href="javascript:">품질입문</a>
					</li>
					<li class="srch-item">
						<a href="javascript:">미래차다각화</a>
					</li>
					<li class="srch-item">
						<a href="javascript:">스마트공장</a>
					</li>
					<li class="srch-item">
						<a href="javascript:">기술지도</a>
					</li>
					<li class="srch-item">
						<a href="javascript:">경영컨설팅</a>
					</li>
					<li class="srch-item">
						<a href="javascript:">경영컨설팅</a>
					</li>
					<li class="srch-item">
						<a href="javascript:">경영컨설팅</a>
					</li>
				</ul>
			</div>
		</section>
		<!-- header - END -->
	</header>