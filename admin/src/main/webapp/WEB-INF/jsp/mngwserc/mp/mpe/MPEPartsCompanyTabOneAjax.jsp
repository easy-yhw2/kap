<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="sqInfoListCnt" value="${ fn:length(sqInfoList.list)}" />
<div class="text-left"><h5>부품사 관리 등록</h5></div>
<fieldset>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm" id="bsnmNo" name="bsnmNo" value="${rtnInfo.bsnmNo}" title="사업자번호" placeholder="사업자등록번호를 입력해주세요." <c:if test="${not empty rtnInfo.bsnmNo or not empty rtnInfo }">readonly</c:if> style="width: 220px;"/>
            <button type="button" class="btn btn-sm" id="checkBtn">인증</button> <span>※ 사업자등록번호 인증 시 부품사/대표자명이 자동으로 입력됩니다.</span>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">부품사명<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm" id="cmpnNm" name="cmpnNm" value="${rtnInfo.cmpnNm}" readonly title="부품사명" style="width: 200px;"/>
        </div>

        <label class="col-sm-1 control-label">부품사명(약식)<span class="star"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="cmpnNfrmlNm" name="cmpnNfrmlNm" value="${rtnInfo.cmpnNfrmlNm}" title="부품사명(약식)" placeholder="부품사명(약식)을 입력해주세요." style="width: 220px;"/>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">대표자명<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm" id="rprsntNm" name="rprsntNm" value="${rtnInfo.rprsntNm}" readonly title="대표자명" style="width: 200px;"/>
        </div>

        <label class="col-sm-1 control-label">부품사코드<span class="star"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="cmpnCd" name="cmpnCd" value="${rtnInfo.cmpnCd}" title="부품사코드" placeholder="부품사코드를 입력해주세요." style="width: 220px;"/>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">구분<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <select class="form-control input-sm" id="ctgryCd" name="ctgryCd" title="구분" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                    <c:if test="${fn:contains(cdList, 'COMPANY01') and cdList.cd ne 'COMPANY01'}">
                        <option value="${cdList.cd}" <c:if test="${rtnInfo.ctgryCd eq cdList.cd}">selected</c:if>>
                                ${cdList.cdNm}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
        <div class="col-sm-5">
            <select class="form-control input-sm" id="sizeCd" name="sizeCd" title="기업규모" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                    <c:if test="${fn:contains(cdList, 'COMPANY020')}">
                        <option value="${cdList.cd}" <c:if test="${rtnInfo.sizeCd eq cdList.cd}">selected</c:if>>
                                ${cdList.cdNm}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">전화번호<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm phoneChk" id="telNo" name="telNo" value="${rtnInfo.telNo}" title="전화번호" placeholder="전화번호를 입력해주세요." style="width: 200px;"/>
        </div>
        <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group" style="z-index:0;width: 220px;">
                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="stbsmDt" value="" readonly="readonly" title="설립일자" />
                <span class="input-group-btn" style="z-index:0;">
                    <button type="button" class="btn btn-inverse input-sm" onclick="jQuery(this).parent().prev().focus();">
                        <em class="ion-calendar"></em>
                    </button>
                </span>
            </div>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">본사주소<span class="star"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnInfo.zipcode}" readonly placeholder="우편번호" style="width: 130px;">
            <input type="button" class="btn btn-sm" id="searchPostCode" value="우편번호 검색"><br>
            <br>
            <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnInfo.bscAddr}" readonly placeholder="기본주소" style="width: 400px;"><br>
            <br>
            <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnInfo.dtlAddr}" placeholder="상세주소" placeholder="상세주소를 입력해주세요." style="width: 400px;">
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">매출액</label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="${rtnInfo.slsPmt}" title="매출액" placeholder="매출액을 입력해주세요." style="width: 220px;"/> 억 원
            <select class="form-control input-sm notRequired" id="slsYear" name="slsYear" title="선택" style="width: 100px;">
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                    <option value="${cdList.cd}" <c:if test="${rtnInfo.slsYear eq cdList.cd}">selected</c:if>>
                            ${cdList.cdNm}
                    </option>
                </c:forEach>
            </select> 년
        </div>
        <label class="col-sm-1 control-label">직원수</label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${rtnInfo.mpleCnt}" title="직원수" placeholder="직원수를 입력해주세요." style="width: 220px;"/> 명
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">주생산품</label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${rtnInfo.mjrPrdct1}" title="주생산품1" placeholder="주생산품(1)을 입력해주세요." style="width: 200px;"/>
            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${rtnInfo.mjrPrdct2}" title="주생산품2" placeholder="주생산품(2)을 입력해주세요." style="width: 200px;"/>
            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${rtnInfo.mjrPrdct3}" title="주생산품3" placeholder="주생산품(3)을 입력해주세요." style="width: 200px;"/>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">품질5스타</label>
        <div class="col-sm-5">
            <select class="form-control input-sm notRequired" id="qlty5StarCd" name="qlty5StarCd" title="품질5스타등급" style="width:auto;">
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                        <option value="${cdList.cd}" <c:if test="${rtnInfo.qlty5StarCd eq cdList.cd}">selected</c:if>>
                                ${cdList.cdNm}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
            <select class="form-control input-sm notRequired" id="qlty5StarYear" name="qlty5StarYear" title="품질5스타연도" style="width: 100px;">
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdList}" varStatus="status">
                    <option value="${cdList}" <c:if test="${rtnInfo.qlty5StarYear eq cdList}">selected</c:if>>
                            ${cdList}
                    </option>
                </c:forEach>
            </select> 년
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">납입5스타</label>
        <div class="col-sm-5">
            <select class="form-control input-sm notRequired" id="pay5StarCd" name="pay5StarCd" title="납입5스타등급" style="width: 100px;">
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                        <option value="${cdList.cd}" <c:if test="${rtnInfo.pay5StarCd eq cdList.cd}">selected</c:if>>
                                ${cdList.cdNm}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
            <select class="form-control input-sm notRequired" id="pay5StarYear" name="pay5StarYear" title="납입5스타연도" style="width: 100px;">
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdList}" varStatus="status">
                    <option value="${cdList}" <c:if test="${rtnInfo.pay5StarYear eq cdList}">selected</c:if>>
                            ${cdList}
                    </option>
                </c:forEach>
            </select> 년
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">기술5스타</label>
        <div class="col-sm-5">
            <select class="form-control input-sm notRequired" id="tchlg5StarCd" name="tchlg5StarCd" title="기술5스타등급" style="width:100px;">
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                        <option value="${cdList.cd}" <c:if test="${rtnInfo.tchlg5StarCd eq cdList.cd}">selected</c:if>>
                                ${cdList.cdNm}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
            <select class="form-control input-sm notRequired" id="tchlg5StarYear" name="tchlg5StarYear" title="기술5스타연도" style="width:100px;">
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdList}" varStatus="status">
                    <option value="${cdList}" <c:if test="${rtnInfo.tchlg5StarYear eq cdList}">selected</c:if>>
                            ${cdList}
                    </option>
                </c:forEach>
            </select> 년
        </div>
    </div>
</fieldset>
<fieldset class="sqInfoArea" <c:if test="${rtnInfo.ctgryCd eq null or rtnInfo.ctgryCd eq 'COMPANY01001'}">style="display:none;"</c:if>>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">SQ 정보</label>
        <div class="col-sm-5">
            <c:forEach items="${sqInfoList.sqInfoList.list}" var="list" varStatus="status">${sqInfoList.sqInfoList}
                <input type="hidden" class="notRequired" id="cbsnSeq${status.count}" name="sqInfoList${status.count}" value="${list.cbsnSeq}"/>
                <input type="text" class="form-control input-sm notRequired" id="nm${status.count}" name="sqInfoList${status.count}" value="${list.nm}" title="SQ 업종" placeholder="SQ 업종입력"/>
                <input type="text" class="form-control input-sm notRequired" id="score${status.count}" name="sqInfoList${status.count}" value="${list.score}" title="점수" placeholder="SQ 점수입력"/>
                <select class="form-control input-sm notRequired" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도" style="width:auto;">
                    <option value="">선택</option>
                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                        <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                ${cdList.cdNm}
                        </option>
                    </c:forEach>
                </select>
                <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="${list.crtfnCmpnNm}" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                </br>
            </c:forEach>
            <c:choose>
                <c:when test="${sqInfoListCnt == 0}">
                    <c:forEach var="i" begin="0" end="${ 3 - sqInfoListCnt }" varStatus="status">
                        <input type="hidden" class="notRequired" id="cbsnSeq${ status.count}" name="sqInfoList${status.count}" value=""/>
                        <input type="text" class="form-control input-sm notRequired" id="nm${status.count}" name="sqInfoList${status.count}" value="" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                        <input type="text" class="form-control input-sm notRequired" id="score${status.count}" name="sqInfoList${status.count}" value="" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                        <select class="form-control input-sm notRequired" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도" style="width:auto;">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select>
                        <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                        </br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="i" begin="0" end="${ 3 - sqInfoListCnt }" varStatus="status">
                        <input type="hidden" class="notRequired" id="cbsnSeq${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value=""/>
                        <input type="text" class="form-control input-sm notRequired" id="nm${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                        <input type="text" class="form-control input-sm notRequired" id="score${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                        <select class="form-control input-sm notRequired" id="year${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" title="평가년도" style="width:auto;">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select>
                        <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                        </br>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">관리자메모</label>
        <div class="col-sm-5">
            <textarea class="form-control input-sm notRequired" id="admMemo" name="admMemo" value="${rtnInfo.admMemo}" title="관리자메모" placeholder="관리자 메모를 입력해주세요." maxlength="500"></textarea>
        </div>
    </div>
</fieldset>