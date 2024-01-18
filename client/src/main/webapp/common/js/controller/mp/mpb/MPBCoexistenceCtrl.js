define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBCoexistenceCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var addCount = 10;
    var search = function(obj)
    {
        cmmCtrl.listFrmAjax(function(respObj) {
            $('.trainings-list-w').empty();
            $('.trainings-list-w').append(respObj);
        }, "./listAjax", $formObj, "POST", "html");
    };

    // set model
    ctrl.model = {
        id : {
            ordFlag : {
                event : {
                    change : function() {
                        search($(this));
                    }
                }
            },
        },
        classname : {
            addMore : {
                event : {
                    click : function() {
                        $('#firstIndex').val($('#recordCountPerPage').val());
                        $('#recordCountPerPage').val(parseInt(addCount+10));

                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리
                            $('.trainings-list-w').append(respObj);
                            addCount = 10+addCount;
                            //전체 갯수
                            var totalCnt = $('#totalCnt').text();

                            if (addCount >= totalCnt) {
                                $('.add-load').hide();
                            } else {
                                $('.item-count').text("("+ addCount + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "POST", "html");
                    }
                }
            },
            moveView : {
                event : {
                    click : function() {
                        var bsnCd = $(this).data("bsnCd");
                        var appctnSeq = $(this).data("appctnSeq");
                        location.href = "./view?bsnCd="+bsnCd+"&appctnSeq="+appctnSeq;
                    }
                }
            },
            cancel : {
                event : {
                    click : function() {
                        if(confirm("신청을 취소하시겠습니까?")) {
                            cmmCtrl.frmAjax(function(data){
                                if (data.respCnt == 100) {
                                    alert("잘못된 접근입니다. 다시 시도바랍니다.");
                                }
                                location.href = "./list";
                            }, "./cancel", $('#cancelFrm'), "post","json");
                        }
                    }
                }
            },
            searchBtn : {
                event : {
                    click : function() {
                        search($(this));
                    }
                }
            }
        },
        immediately : function(){
            cmmCtrl.setCalendar()

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

