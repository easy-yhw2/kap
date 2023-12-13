define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/MPMemberPartsController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    //사업자등록 체크
    let bsnmChk = false;

    //사업자 등록 여부 체크
    let bsnmOldNewChk = false;

    let clickIndex = 1;

    let $formObj = $("#formNextSubmit");

    // set model
    ctrl.model = {
        id : {
            bsnmNo : {
                event : {
                    click : function() {
                        bsnmChk = false;
                        $(".for-status-chk2").removeClass('satisfy');
                        $(".cmpn_nm_new").val('');
                        $(".rprsnt_nm").val('');

                    }
                }
            },



            // do something...
            ctgryCd : {
                event : {
                    change : function() {
                        if($(this).val() == 'COMPANY01001') {
                                btnSqInit();
                                $(".gubunOne").show();
                                $(".gubunTwo").hide();
                        } else if($(this).val() == 'COMPANY01002'){
                                $(".gubunOne").hide();
                                $(".gubunTwo").show();
                        }
                    }
                }
            },
            nextBtn : {
                event : {
                    click : function() {
                            if(!bsnmChk) {
                                alert(msgCtrl.getMsg("fail.mp.join.al_036"));
                                return false;
                            }
                            if($("#bsnmNo").val().trim() =='' || $("#bsnmNo").val() == undefined) {
                                alert(msgCtrl.getMsg("fail.mp.join.al_018"));
                                $("#bsnmNo").focus();
                                return false;
                            }
                            if($(".cmpn_nm_new").val().trim() =='' || $(".cmpn_nm_new").val() == undefined) {
                                alert(msgCtrl.getMsg("fail.mp.join.al_037"));
                                $("#cmpn_nm_new").focus();
                                return false;
                            }
                            if(!bsnmOldNewChk) {
                                if ($("#ctgryCd").val().trim() == '' || $("#ctgryCd").val() == undefined ) {
                                    alert(msgCtrl.getMsg("fail.mp.join.al_027"));
                                    $("#ctgryCd").focus();
                                    return false;
                                }
                                if ($("#sizeCd").val().trim() == '' || $("#sizeCd").val() == undefined) {
                                    alert(msgCtrl.getMsg("fail.mp.join.al_028"));
                                    $("#sizeCd").focus();
                                    return false;
                                }
                                if ($("#stbsmDt").val().trim() == '' || $("#stbsmDt").val() == undefined || $("#stbsmDt").val().length != 10) {
                                    alert(msgCtrl.getMsg("fail.mp.join.al_029"));
                                    $("#stbsmDt").focus();
                                    return false;
                                }
                                if($("#telNo").val().trim() == '' || $("#telNo").val() == undefined) {
                                    alert(msgCtrl.getMsg("fail.mp.join.al_030"));
                                    $("#telNo").focus();
                                    return false;
                                }
                                if ($("#zipcode").val().trim() == '' || $("#zipcode").val() == undefined ||
                                    $("#bscAddr").val().trim() == '' || $("#bscAddr").val() == undefined ||
                                    $("#dtlAddr").val().trim() == '' || $("#dtlAddr").val() == undefined) {
                                    alert(msgCtrl.getMsg("fail.mp.join.al_031"));
                                    $("#dtlAddr").focus();
                                    return false;
                                }
                            }
                        var list1 = [];
                        list1.push("");
                        list1.push($("#nm1").val());
                        list1.push($("#score1").val());
                        list1.push($("#year1").val());
                        list1.push($("#crtfnCmpnNm1").val());

                        var list2 = [];
                        list2.push("");
                        list2.push($("#nm2").val());
                        list2.push($("#score2").val());
                        list2.push($("#year2").val());
                        list2.push($("#crtfnCmpnNm2").val());

                        var list3 = [];
                        list3.push("");
                        list3.push($("#nm3").val());
                        list3.push($("#score3").val());
                        list3.push($("#year3").val());
                        list3.push($("#crtfnCmpnNm3").val());

                        $(".sqInfoList1").val(list1);
                        $(".sqInfoList2").val(list2);
                        $(".sqInfoList3").val(list3);
                        $(".bsnmChk").val(bsnmChk);
                        let targetPage= "/member/mp-user-join";
                        document.getElementById("formNextSubmit").action = targetPage; // form의 action 속성 변경
                        document.getElementById("formNextSubmit").submit(); // form 제출
                    }
                }
            },
            //우편검색
            searchPostCode : {
                event : {
                    click : function() {
                        // searchPostCode();
                        cmmCtrl.searchPostCode(500,500,"zipcode","bscAddr","dtlAddr");

                    }
                }
            },

        },
        classname : {
            // do something...
            btnBack : {
                event : {
                    click : function () {
                        if (confirm(msgCtrl.getMsg("confirm.backChk"))) {
                            history.back();
                        }
                    }
                }
            },
            //사업자 등록 번호 인증
            btnCmpnChk : {
                event : {
                    click : function() {
                        //1. db를 조회 한다.
                        if($("#bsnmNo").val() =='' || $("#bsnmNo").val() == undefined) {
                            alert(msgCtrl.getMsg("fail.mp.join.al_018"));
                            bsnmChk = false;
                            return ;
                        }
                        jQuery.ajax({
                            url : "/member/bsnm-select/"+$("#bsnmNo").val(),
                            type : "get",
                            success : function(data)
                            {
                                //2. 있다면 디비 정보를 조회 후 뿌려준다.
                                if(data.rtnData.list.length >=1) {
                                    $(".for-status-chk2").addClass('satisfy');
                                    let datas = data.rtnData.list[0];
                                    $(".old").show();
                                    $(".new").hide();
                                    $(".cmpn_nm").text(datas.cmpnNm);
                                    $(".rprsnt_nm").text(datas.rprsntNm);
                                    $(".gubun").text(datas.ctgryNm);
                                    $(".addr").text(datas.bscAddr+" "+datas.dtlAddr);
                                    bsnmChk = true;
                                    bsnmOldNewChk = true;
                                } else {
                                    bsnmOldNewChk = false;
                                    //3. 없다면 나이스 api 호출을 한다.
                                    $(".old").hide();
                                    $(".new").show();
                                    jQuery.ajax({
                                        url : "/nice/comp-chk",
                                        type : "post",
                                        data :
                                            {
                                                "compNum" : $("#bsnmNo").val()
                                            },
                                        success : function(data)
                                        {
                                            //4. 있다면 정보를 입력한다.
                                            //5. 없다면 정보가 없다고 한다.
                                            if(data.rsp_cd=='P000') {
                                                if(data.result_cd == '01') {
                                                    if(data.comp_status == '1') {
                                                        $(".cmpn_nm_new").val(data.comp_name);
                                                        $(".rprsnt_nm").val(data.representive_name);
                                                        $(".for-status-chk2").addClass('satisfy');
                                                        bsnmChk = true;
                                                    } else {
                                                        alert(msgCtrl.getMsg("fail.mp.join.al_019"));
                                                        $("#bsnmNo").val("");
                                                        $(".for-status-chk2").removeClass('satisfy');
                                                        bsnmChk = false;
                                                        return false;
                                                    }
                                                } else {
                                                    alert(msgCtrl.getMsg("fail.mp.join.al_019"));
                                                    $("#bsnmNo").val("");
                                                    $(".for-status-chk2").removeClass('satisfy');
                                                    bsnmChk = false;
                                                    return false;
                                                }
                                            } else {
                                                alert(msgCtrl.getMsg("fail.mp.join.al_019"));
                                                $("#bsnmNo").val("");
                                                $(".for-status-chk2").removeClass('satisfy');
                                                bsnmChk = false;
                                                return false;
                                            }
                                        },
                                        error : function(xhr, ajaxSettings, thrownError)
                                        {
                                            cmmCtrl.errorAjax(xhr);
                                            jQuery.jstree.rollback(data.rlbk);
                                        }
                                    });

                                }
                            },
                            error : function(xhr, ajaxSettings, thrownError)
                            {
                                cmmCtrl.errorAjax(xhr);
                                jQuery.jstree.rollback(data.rlbk);
                            }
                        });
                    }
                }
            },
            //회사전화번호
            telNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneNumber.startsWith('02')) {
                            if (phoneLen >= 3 && phoneLen <= 6) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                            } else if (phoneLen > 6) {
                                if (phoneLen == 9) {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                                }
                            }
                        } else {
                            if (phoneLen > 3 && phoneLen <= 7) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                            } else if (phoneLen > 7) {
                                if (phoneLen == 10) {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                                }
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },

            //sq정보 추가
            btnSqInfoPlus : {
                event : {
                    click : function() {
                        if(clickIndex >=3) {
                            alert(msgCtrl.getMsg("fail.mp.join.al_026"));
                            return false;
                        }
                        $(".lastBtnIndex" + clickIndex).empty();
                        clickIndex++;
                        $(".data-line"+clickIndex).show();
                        $(".lastBtnIndex"+clickIndex).append('<button class="btn-text-icon delete btnSqInfoMinus" type="button"><span>삭제</span></button>');
                        $(".lastBtnIndex"+clickIndex).append('<button class="btn-solid small gray-bg btn-add-line btnSqInfoPlus" type="button"><span>SQ 정보 추가</span></button>');
                    }
                }
            },

            //sq정보 삭제
            btnSqInfoMinus : {
                event : {
                    click : function() {
                        if(clickIndex <=1) {
                            return false;
                        }
                        $(".lastBtnIndex" + clickIndex).empty();
                        $(".data-line"+clickIndex).hide();
                        clickIndex--;
                        $(".lastBtnIndex"+clickIndex).append('<button class="btn-text-icon delete btnSqInfoMinus" type="button"><span>삭제</span></button>');
                        $(".lastBtnIndex"+clickIndex).append('<button class="btn-solid small gray-bg btn-add-line btnSqInfoPlus" type="button"><span>SQ 정보 추가</span></button>');
                    }
                }
            },
        },
        immediately : function() {
            btnSqInit();
        }

    };

    //sq정보 초기화
    function btnSqInit() {
        for(let i = clickIndex ; i > 0 ; i--) {
            $(".data-line"+i).hide();
            $(".lastBtnIndex" + i).empty();
            $("#nm"+i).val('');
            $("#year"+i).val('');
            $("#score"+i).val('');
            $("#crtfnCmpnNm"+i).val('');
        }
        clickIndex = 1;
        $(".data-line"+clickIndex).show();
        $(".lastBtnIndex"+clickIndex).append('<button class="btn-text-icon delete btnSqInfoMinus" type="button"><span>삭제</span></button>');
        $(".lastBtnIndex"+clickIndex).append('<button class="btn-solid small gray-bg btn-add-line btnSqInfoPlus" type="button"><span>SQ 정보 추가</span></button>');
    }

    ctrl.exec();

    return ctrl;
});