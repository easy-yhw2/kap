<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="csList" value="${rtnDto.list}"/>
<%
  String bsnmNo = request.getParameter("bsnmNo");
%>
<input type="hidden" class="bsnmNo" value="<%=bsnmNo%>">
<div class="cont-wrap" data-controller="controller/cb/cbb/CBBManageConsultWriteCtrl">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <div class="sub-top-vis-area apply-page consult-biz">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move"></span></p>
            <div class="apply-step-w">
                <div class="for-move">
                    <div class="step-list completed"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                        <p class="step-num">1</p>
                        <p class="step-con">기본정보</p>
                    </div>
                    <div class="step-list ongoing">
                        <p class="step-num">2</p>
                        <p class="step-con">정보입력</p>
                    </div>
                    <div class="step-list">
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
            <div class="lnb-area">
                <div class="for-motion">
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>기술지도</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu active" href="javascript:"><span>경영컨설팅</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                </div>
            </div>

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">상주경영컨설팅 신청정보를 입력해주세요</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">부품사 규모<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="opt-group">
                                                        <c:forEach var="cpSize" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                            <c:if test="${fn:contains(cpSize.cd,'COMPANY02') && cpSize.cd ne 'COMPANY02'}">
                                                                <div class="form-radio">
                                                                    <input type="radio" id="${cpSize.cd}" value=""${cpSize.cd}" name="sizeCd">
                                                                    <label for="${cpSize.cd}">${cpSize.cdNm}</label>
                                                                </div>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">주고객사<br class="only-pc"/>납품비율<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-inner-line">
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="업체명 입력">
                                                        </div>
                                                        <div class="form-input">
                                                            <input type="text" placeholder="비율(%) 입력">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-inner-line">
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="업체명 입력">
                                                        </div>
                                                        <div class="form-input">
                                                            <input type="text" placeholder="비율(%) 입력">
                                                        </div>
                                                        <!-- <div class="btn-wrap btn-delete-line">

                                                        </div> -->
                                                        <!-- 업체 추가 버튼은 마지막 추가된 리스트에 표시됩니다 -->
                                                        <div class="btn-wrap">
                                                            <button class="btn-text-icon delete" type="button"><span>삭제</span></button>
                                                            <button class="btn-solid small gray-bg btn-add-line" type="button"><span>업체 추가</span></button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">국내완성차<br class="only-pc"/>의존율<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-inner-line">
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="업체명 입력">
                                                        </div>
                                                        <div class="form-input">
                                                            <input type="text" placeholder="의존율(%) 입력">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-inner-line">
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="업체명 입력">
                                                        </div>
                                                        <div class="form-input">
                                                            <input type="text" placeholder="의존율(%) 입력">
                                                        </div>
                                                        <!-- <div class="btn-wrap btn-delete-line">

                                                        </div> -->
                                                        <!-- 업체 추가 버튼은 마지막 추가된 리스트에 표시됩니다 -->
                                                        <div class="btn-wrap">
                                                            <button class="btn-text-icon delete" type="button"><span>삭제</span></button>
                                                            <button class="btn-solid small gray-bg btn-add-line" type="button"><span>업체 추가</span></button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">해외 의존율<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="number" placeholder="의존율(%) 입력">
                                                            <p class="unit-txt">%</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">국내 매출액<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="number" placeholder="매출액 입력">
                                                            <p class="unit-txt">억 원</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">해외 매출액<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="number" placeholder="매출액 입력">
                                                            <p class="unit-txt">억 원</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">전체 매출액<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="number" placeholder="매출액 입력">
                                                            <p class="unit-txt">억 원</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">자동차부품 <br class="only-pc"/>매출액<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="number" placeholder="매출액 입력">
                                                            <p class="unit-txt">억 원</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">자동차부품 외 <br class="only-pc"/>매출액<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="number" placeholder="매출액 입력">
                                                            <p class="unit-txt">억 원</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">공장주소<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="middle-line">
                                                        <div class="form-checkbox">
                                                            <input type="checkbox" id="sameAsHQChk" name="">
                                                            <label for="sameAsHQChk">본사와 동일</label>
                                                        </div>
                                                    </div>
                                                    <div class="middle-line">
                                                        <div class="form-address">
                                                            <div class="form-group">
                                                                <div class="form-input">
                                                                    <input type="text" placeholder="우편번호" value="12345" readonly>
                                                                </div>
                                                                <div class="form-input w-longer">
                                                                    <input type="text" placeholder="주소" value="서울시 구로구 디저털로32길 20" readonly>
                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <button class="btn-solid small gray-bg" type="button"><span>우편번호 찾기</span></button>
                                                                </div>
                                                            </div>
                                                            <div class="form-group">
                                                                <div class="form-input w-longest">
                                                                    <input type="text" placeholder="상세주소 입력" value="서울시 구로구 디저털로32길 20">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">소재지역<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select id="" title="소재지 선택">
                                                                <option value="" selected>선택</option>
                                                                <option value="">시, 도 대분류 선택1</option>
                                                                <option value="">시, 도 대분류 선택2</option>
                                                                <option value="">시, 도 대분류 선택3</option>
                                                            </select>
                                                        </div>
                                                        <div class="form-select">
                                                            <select id="" title="소재지 선택">
                                                                <option value="" selected>선택</option>
                                                                <option value="">군, 구 중분류 선택1</option>
                                                                <option value="">군, 구 중분류 선택2</option>
                                                                <option value="">군, 구 중분류 선택3</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">홈페이지 주소</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="홈페이지 주소 입력">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청사유<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="opt-group">
                                                        <div class="form-radio">
                                                            <input type="radio" id="applyMotiveRadio1" name="applyMotiveRadioSet">
                                                            <label for="applyMotiveRadio1">자발적 신청</label>
                                                        </div>
                                                        <div class="form-radio">
                                                            <input type="radio" id="applyMotiveRadio2" name="applyMotiveRadioSet">
                                                            <label for="applyMotiveRadio2">1차부품사 권유</label>
                                                        </div>
                                                        <div class="form-radio">
                                                            <input type="radio" id="applyMotiveRadio3" name="applyMotiveRadioSet">
                                                            <label for="applyMotiveRadio3">완성차 권유</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">품질담당 인원<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="number" placeholder="인원수 입력">
                                                            <p class="unit-txt">명</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">경영컨설팅 자문신청 내용을 입력해주세요</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청분야<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select id="" title="신청사항 선택">
                                                                <option value="" selected="">선택</option>
                                                                <option value="">선택1</option>
                                                                <option value="">선택2</option>
                                                                <option value="">선택3</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">컨설팅요청 <br class="only-pc"/>세부내용<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-textarea">
                                                            <textarea name="" id="" cols="" rows="" placeholder="세부내용 입력"></textarea>
                                                            <div class="check-byte">
                                                                <p class="txt"><span class="current-byte">0</span>자</p>
                                                                <p class="txt"><span class="max-byte">500</span>자</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">경영컨설팅 부품사 선정을 위한 첨부파일을 등록해주세요</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="noti-txt-w">
                                                        <p class="f-body2">상주경영컨설팅 신청 시 회사소개서(자율 양식)와 개선활동 추진계획서가 필수로 첨부되어야 합니다. </p>
                                                        <P class="bullet-noti-txt f-caption2">* 개선활동 추진계획서는 아래 [양식 다운로드] 후 양식에 맞게 작성 후 첨부 부탁드립니다.</P>
                                                        <P class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</P>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">회사소개서</p>
                                                    <div class="form-group">
                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <p class="data-title f-body1">개선활동 추진계획서</p>
                                                        <div class="form-group">
                                                            <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                <div class="file-list">
                                                                    <p class="file-name"><span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span><span class="unit">pdf</span></p>
                                                                    <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                                </div>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" id="searchFile">
                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                            </div>
                                                            <div class="btn-wrap btn-down-wrap">
                                                                <a class="btn-text-icon download" href="javascript:" download><span>양식 다운로드</span></a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- <div class="inner-line">
                                                      <div class="btn-wrap">
                                                        <a class="btn-text-icon download" href="javascript:" download><span>양식 다운로드</span></a>
                                                      </div>
                                                    </div> -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">경영컨설팅을 위한 약관을 확인해주세요</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">이용 약관<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <!-- 2024-01-02 약관 추가 -->
                                                <div class="agree-box">
                                                    <div class="gray-bg-sec narrow-pad">
                                                        <div class="paragraphs">
                                                            <p class="txt f-body2">재단의 경영컨설팅은 전액 무상으로 지원되는 사업으로서, 지도 활동을 통해 달성한 유·무형의 성과는 전적으로 지도업체에 귀속되나, 재단의 설립취지인 자동차부품산업 발전을 위해 그 성과를 활용(지도 우수기업 선정 및 정부 등 유공자 포상 추천, 동종업계 수평전개를 위한 개선사례 교육/발표/현장공개 등)할 수 있으며, 구체적인 내용은 재단과 지도업체의 협의를 통해 진행됩니다.</p><!-- 2023-12-26 이용약관 문구 변경 -->
                                                        </div>
                                                    </div>
                                                    <div class="form-group align-right">
                                                        <div class="form-checkbox">
                                                            <input type="checkbox" id="agreeChk" name="">
                                                            <label for="agreeChk">약관에 동의합니다.</label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- // 2024-01-02 약관 추가 -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg" href="javascript:"><span>취소</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg" href="javascript:"><span>신청하기</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</div>
<!-- content 영역 end -->