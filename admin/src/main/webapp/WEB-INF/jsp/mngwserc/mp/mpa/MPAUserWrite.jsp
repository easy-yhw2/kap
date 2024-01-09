<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="container-fluid">
    <div class="card-body" data-controller="controller/mp/mpa/MPAUserWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.detailsKey}" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="lgnSsnId" value="${rtnData.lgnSsnId}">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />

            <h6 class="mt-lg"> 회원 기본 정보 </h6>

            <div class="table-responsive col-sm-12 p0 m0">
                <table class="table">
                    <colgroup>
                        <col style="width:10%;">
                        <col style="width:40%;">
                        <col style="width:10%;">
                        <col style="width:40%;">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">아이디</th>
                        <td>${rtnInfo.id}</td>
                        <th scope="row" class="bg-gray-lighter">이름</th>
                        <td>${rtnInfo.name}</td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">휴대폰번호</th>
                        <td>${rtnInfo.hpNo}</td>
                        <th scope="row" class="bg-gray-lighter">이메일</th>
                        <td>${rtnInfo.email}</td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">가입일</th>
                        <td> ${ kl:convertDate(rtnInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
                        <th scope="row" class="bg-gray-lighter">최종 접속일</th>
                        <td>${ kl:convertDate(rtnInfo.lastLgnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <ul class="nav nav-tabs" id="myTabs">
                <li class="active tabClick"><a data-toggle="tab" href="#dtl">회원 상세 정보</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#pur">상생사업 신청내역</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#chat">1:1문의</a></li>
                <span class="dtl-tab" style="margin-left:55%"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
            </ul>

            <div class="tab-content">
                <div id="dtl" class="tab-pane fade in active">
                    <div id="tab1">
                    </div>
                </div>
                <div id="pur" class="tab-pane fade">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            미래차공모전 신청내역 (총 <span id="listContainerTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">사업년도</th>
                            <th class="text-center">팀장명</th>
                            <th class="text-center">참여구분</th>
                            <th class="text-center">주제</th>
                            <th class="text-center">시상부문</th>
                            <th class="text-center">서류심사</th>
                            <th class="text-center">1차결과</th>
                            <th class="text-center">최종결과</th>
                            <th class="text-center">신청일</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                            <tbody id="listContainer"/>
                    </table>
                    <!-- 페이징 버튼 -->
                    <div id="pagingContainer"></div>
                </div>
                <div id="chat" class="tab-pane fade">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            1:1문의 목록 (총 <span id="listContainerInqrTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                    </div>

                <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">1차 문의유형</th>
                            <th class="text-center">2차 문의유형</th>
                            <th class="text-center">제목</th>
                            <th class="text-center">문의등록일</th>
                            <th class="text-center">답변등록일</th>
                            <th class="text-center">진행상태</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="listContainerInqr"/>
                    </table>
                    <!-- 페이징 버튼 -->
                    <div id="pagingContainerInqr"></div>
                </div>
            </div>
            <hr />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-param="${strPam}">목록</button>
                </div>
                <div style="float:right">
                    <button type="submit" class="btn btn-sm btn-success dtl-tab" id="btnSave" >저장</button>
                </div>
            </div>
        </form>
    </div>
    <div class="dtl-tab">
        <h5>수정이력</h5>
        <table class="table">
            <colgroup>
                <col style="width:10%;">
                <col style="width:40%;">
                <col style="width:10%;">
                <col style="width:40%;">
            </colgroup>
            <tbody>
            <tr>
                <th scope="row" class="bg-gray-lighter">최종 수정자 </th>
                <td>${rtnInfo.modName}(${rtnInfo.modId})</td>
                <th scope="row" class="bg-gray-lighter">최종 수정일시 </th>
                <td>${ kl:convertDate(rtnInfo.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>


