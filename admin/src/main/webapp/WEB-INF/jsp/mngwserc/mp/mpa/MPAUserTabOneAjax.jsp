<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<form class="form-horizontal" id="frmData" name="frmData" method="post" >
    <input type="hidden" id="oldEmail" name="oldEmail" value=${rtnDtl.email} />
    <input type="hidden" id="id" name="id" value=${rtnDtl.id} />
</form>
        <fieldset>
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">이름<span class="star"> *</span></label>
                <p class="col-sm-11 form-control-static">${rtnDtl.name}</p>
            </div>
        </fieldset>
        <fieldset>
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">아이디<span class="star"> *</span></label>
                <p class="col-sm-11 form-control-static">${rtnDtl.id}</p>
            </div>
        </fieldset>
        <fieldset>
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">비밀번호<span class="star"> *</span></label>
               <button type="button" class="btn btn-secondary"  id="btnPwdInit" data-id="${rtnDtl.id}" >비밀번호 초기화</button>
            </div>
        </fieldset>
        <fieldset>
            <div class="form-group text-sm form-inline">
                <label class="col-sm-1 control-label">주소<span class="star"> *</span></label>
                <div class="col-sm-5">
                    <input type="text" class="form-control input-sm" id="zipcode" title="우편번호" name="zipcode" readonly  value="${rtnDtl.zipcode}"/>
                    <input type="button" class="btn btn-sm" id="searchPostCode" value="우편번호 검색"><br>
                    <br>
                    <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnDtl.bscAddr}" readonly placeholder="기본주소" style="width: 400px;"/><br>
                    <br>
                    <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnDtl.dtlAddr}" title="우편번호" placeholder="상세주소 입력" maxlength="50" style="width: 400px;"/>
                </div>
            </div>
        </fieldset>
        <fieldset>
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">일반 전화번호</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control input-sm notRequired" id="telNo" name="telNo" value="${rtnDtl.telNo}"  oninput="this.value=this.value.replace(/[^0-9]/g, '')" />
                </div>
            </div>
        </fieldset>
        <fieldset>
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                <div class="col-sm-3">
                    <div class="input-group">

                        <input type="text" class="form-control input-sm " id="email" name="email" value="${rtnDtl.email}" title="이메일" maxlength="50" oninput="this.value=this.value.replace(/[^\x00-\x7F]/g, '')"/>
                        <span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="dupEmail">중복확인</button></span>
                    </div>
                </div>
            </div>
        </fieldset>
        <fieldset>
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">생년월일</label>
                <p class="col-sm-5 form-control-static">${ rtnDtl.birth}</p>
                <label class="col-sm-1 control-label">성별</label>
                <p class="col-sm-5 form-control-static">${rtnDtl.gndr == '1' ? '남' : '여'} </p>
            </div>
        </fieldset>

        <fieldset class="last-child">
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">SMS 수신여부<span class="star"> *</span></label>
                <div class="col-sm-5">
                    <c:set var="useYn" value="${rtnDtl.ntfySmsRcvYn.toString()}" />
                    <label class="radio-inline c-radio">
                        <input type="radio" name="ntfySmsRcvYn" value="Y" title="수신"  ${rtnDtl.ntfySmsRcvYn.toString() == 'Y' ? 'checked' : ''}  />
                        <span class="ion-record"></span> 수신
                    </label>
                    <label class="radio-inline c-radio">
                        <input type="radio" name="ntfySmsRcvYn" value="N" title="수신"  ${rtnDtl.ntfySmsRcvYn.toString() == 'N' ? 'checked' : ''}  />
                        <span class="ion-record"></span> 수신 안함
                    </label>
                </div>
                <label class="col-sm-1 control-label">이메일 수신여부<span class="star"> *</span></label>
                <div class="col-sm-5">
                    <c:set var="useYn" value="${rtnDtl.ntfyEmailRcvYn.toString()}" />
                    <label class="radio-inline c-radio">
                        <input type="radio" name="ntfyEmailRcvYn" value="Y" title="수신" ${rtnDtl.ntfyEmailRcvYn.toString() == 'Y' ? 'checked' : ''} />
                        <span class="ion-record"></span> 수신
                    </label>
                    <label class="radio-inline c-radio">
                        <input type="radio" name="ntfyEmailRcvYn" value="N" title="수신" ${rtnDtl.ntfyEmailRcvYn.toString() == 'N' ? 'checked' : ''} />
                        <span class="ion-record"></span> 수신 안함
                    </label>
                </div>
            </div>
        </fieldset>
    </div>
</div>
    <h5>등록/수정이력</h5>
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
                <td>${rtnDtl.modName}(${rtnDtl.modId})</td>
                <th scope="row" class="bg-gray-lighter">최종 수정일시 </th>
                <td>${ kl:convertDate(rtnDtl.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
            </tr>
            </tbody>
        </table>

