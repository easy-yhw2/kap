define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbj/WBJRoundListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var langCd = $("#langCd").val();
    var menuSeq = $("#typeCd").val();

    //목록 조회
    var search = function(page){
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "/mngwserc/wb/wbja/select", $formObj, "POST", "html");
    }

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        //검색버튼 클릭시
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            },
            //데이터 삭제
            btnChooseDelete : {
                event : {
                    click : function() {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        var delType = frmDataObj.data("delType");
                        if (delActCnt > 0)
                        {
                            if (confirm(msgCtrl.getMsg("confirm.del"))) {
                                cmmCtrl.frmAjax(function(respObj) {
                                    if(respObj.optEpisdCnt == '0'){
                                        //삭제 전송
                                        cmmCtrl.frmAjax(function(respObj){
                                            if(respObj != undefined && respObj.respCnt > 0){
                                                var msg = msgCtrl.getMsg("success.del.target.none");
                                                if(typeof delType!= "undefined" && typeof msgCtrl.getMsg("success.del.target." + delType) != "undefined"){
                                                    msg = msgCtrl.getMsg("success.del.target." + delType);
                                                }
                                                alert(msg);
                                                $formObj.find("#btnSearch").click();
                                            }
                                            else{
                                                alert(msgCtrl.getMsg("fail.act"));
                                            }
                                        }, "./delete", frmDataObj, "POST", "json");
                                    }else{
                                        alert("신청정보가 존재하여 삭제할 수 없습니다.");
                                    }
                                }, "/mngwserc/wb/wbja/getRsumeCnt", $formObj, "post", "json")
                            }
                        }
                        else
                        {
                            if(typeof delType!= "undefined")
                            {
                                alert(msgCtrl.getMsg("fail.del.target." + frmDataObj.data("delType")));
                            }
                            else
                            {
                                alert(msgCtrl.getMsg("fail.targetBoard"));
                            }

                            return;
                        }
                    }
                }
            },
        },
        classname : {
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            search();
                        }
                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        location.href = "./write?" + $formObj.serialize();
                    }
                }
            },
            //페이징 목록 갯수
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
                    }
                }
            }
        },
        immediately : function() {
            //리스트 조회
            search();
        }
    };


    ctrl.exec();

    return ctrl;
});

