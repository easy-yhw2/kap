define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbl/WBLSurveyStep2Ctrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);

    var questionSet = function(surveyType) {


        $(".surveyList").each(function () {
            var $surveyObj = $(this);
            var surveyTypeData = $(this).data('survey-type');
            var cnt = 1;
            var subCnt = 1;
            $("." + surveyTypeData).each(function (index) {                         // 질문, 하위질문 번호를 구분하고 순서를 셋팅
                if ($(this).find('input[name=dpth]').val() == '2') {
                    $("." + surveyTypeData + "questionTxt:eq(" + index + ")").text("Q" + eval(cnt - 1) + "-" + subCnt);
                    $(this).addClass(eval(cnt - 1) + "-" + subCnt);
                    subCnt = subCnt + 1;
                } else {
                    $("." + surveyTypeData + "questionTxt:eq(" + index + ")").text("Q" + cnt);
                    cnt = cnt + 1;
                    subCnt = 1;
                }
            });

        });
    }

    // set model
    ctrl.model = {
        id : {
            btnSubmit : {
                event : {
                    click : function() {
                        $formObj.submit();
                    }
                }
            },
        },
        classname : {
            answer : {
                event : {
                    click : function() {
                        var surveyList = $(this).closest('.survey-list');
                        var nextNo = $(this).data("next-no");

                        if (nextNo != ''){
                            surveyList.find(".survey-list-inner:not(:eq(0)) input:radio").prop('checked',false).hide();

                            surveyList.find('.survey-list-inner:not(:eq(0))').hide();
                            var nextNoSplit = nextNo.split(',');
                            $(nextNoSplit).each(function(i){
                                surveyList.find('.'+nextNoSplit[i]).show();
                            });
                        }
                    }
                }
            },
        },
        immediately : function(){

            questionSet('WIN');

            // 유효성 검사
            $formObj.validation({
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./insert";

                        var svRepnMst = {};
                        svRepnMst.srvSeq = $formObj.find("input[name=srvSeq]").val();
                        svRepnMst.rfncCd = $formObj.find("input[name=rfncCd]").val();
                        svRepnMst.regId = $formObj.find("input[name=rfncCd]").val();

                        svRepnMst.svSurveyQstnRspnDtlList = new Array();

                        $(".surveyList").each(function(){
                            var svQstnDtl = {};
                            var $surveyListObj = $(this);
                                svQstnDtl.srvTypeCd = $surveyListObj.find("input[name=srvTypeCd]").val();
                                svQstnDtl.qstnSeq = $surveyListObj.find("input[name=qstnSeq]").val();


                                if ($(this).find(".exmplList").size() > 0 ) {
                                    svQstnDtl.svSurveyExmplRspnDtlList = new Array();
                                    $(this).find(".exmplList").each(function(index){
                                        var svExmplDtl = {};
                                        if(svQstnDtl.srvTypeCd == 'QST03' || svQstnDtl.srvTypeCd == 'QST04') {
                                            svExmplDtl.sbjctRply = $(this).find(".answer").val();
                                            svExmplDtl.exmplSeq = $(this).find(".answer:checked").val();
                                        }else{
                                            svExmplDtl.exmplSeq = $(this).find(".answer:checked").val();
                                        }

                                        if (typeof svExmplDtl.exmplSeq != 'undefined' || typeof svExmplDtl.sbjctRply != 'undefined'){
                                            svQstnDtl.svSurveyExmplRspnDtlList.push(svExmplDtl);
                                        }
                                    });
                                }
                            svRepnMst.svSurveyQstnRspnDtlList.push(svQstnDtl);
                                console.log(svRepnMst);
                        });

                        cmmCtrl.jsonAjax(function(data){
                            location.href = "./step3"
                        }, actionUrl, svRepnMst, "text")
                    }
                }
            });

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

