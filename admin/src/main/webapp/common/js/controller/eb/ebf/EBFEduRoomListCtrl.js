define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/ebf/EBFEduRoomListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    //목록 조회
    var search = function(page){
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {

            //CALLBACK 처리
            ctrl.obj.find("#eduRoomListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#eduRoomlistContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "eduRoomListContainer", "eduRoomPagingContainer");
        }, "/mngwserc/eb/ebf/select", $formObj, "GET", "html");
    }

    //레이어 구분, 규모 목록 조회
    var codeSelect = function () {
        cmmCtrl.frmAjax(function(respObj) {
            ctrl.obj.find("#selectBoxArea").html(respObj);
        }, "/mngwserc/eb/ebf/codeSelect", $formObj, "GET", "html",'',false);
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

            //검색 초기화
            btnRefresh : {
                event : {
                    click : function() {
                        //FORM 데이터 전체 삭제
                        var pageIndex 	= $formObj.find("#pageIndex").val();
                        var listRowSize = $formObj.find("#listRowSize").val();
                        var pageRowSize = $formObj.find("#pageRowSize").val();
                        var csrfKey 	= $formObj.find("#csrfKey").val();
                        var srchLayer 	= $formObj.find("input[name=srchLayer]").val();
                        $formObj.clearForm();
                        //FORM 전송 필수 데이터 삽입
                        $formObj.find("#pageIndex").val( pageIndex );
                        $formObj.find("#listRowSize").val( listRowSize );
                        $formObj.find(".listRowSizeContainer").val( listRowSize );
                        $formObj.find("#pageRowSize").val( pageRowSize );
                        $formObj.find("#csrfKey").val( csrfKey );
                        $formObj.find("input[name=srchLayer]").val( srchLayer );

                        //캘린더 초기화
                        cmmCtrl.setPeriod(this, "", "", false);

                        //검색 로직 실행
                        $formObj.find("#btnSearch").click();
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
                        var rsumeCd = $(this).data("rsumeCd");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        $formObj.find("input[name=rsumeCd]").val(rsumeCd);
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
            },
            //검색 레이어에서 선택시 호출
            btnEduPlaceLayerChoice:{
                event : {
                    click: function(){
                        var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                        if( choiceCnt > 1){
                            alert(msgCtrl.getMsg("fail.eb.ebf.notSrchPlaceCom1"));
                        } else if(choiceCnt == 0){
                            alert(msgCtrl.getMsg("fail.eb.ebf.notSrchPlaceCom"));
                        }else{
                            var clickObj = {};
                            clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();
                            var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text());
                            var rgnsNm= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find("td").eq(3).text().trim();//지역
                            var addr= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find("td").eq(4).text().trim();//주소
                            var rprsntTelNo= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find("td").eq(5).text().trim();//대표번호

                            clickObj.titl = titl;//교육장명
                            clickObj.rgnsNm = rgnsNm;//지역
                            clickObj.addr = addr;//주소
                            clickObj.rprsntTelNo = rprsntTelNo;//대표번호



                            clickObj.titl = titl;
                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }
                    }
                }
            },

            //리스트 전체 체크박스 선택시
            checkboxAll : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxSingle가 변경됨
                        var trgtArr = $(this).closest("div").find(".placeCheckboxSingle");
                        if (trgtArr.length > 0)
                        {
                            var isChecked = false;
                            if($(this).is(":checked"))
                            {
                                isChecked = true;
                            }
                            $.each(trgtArr, function(){
                                $(this).prop("checked", isChecked);
                            })
                        }
                    }
                }
            },
            placeCheckboxSingle : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxAll이 변경됨
                        var trgtObj = $(this).closest("div");
                        var allCbxCnt = trgtObj.find(".placeCheckboxSingle").length;
                        var selCbxCnt = trgtObj.find(".placeCheckboxSingle:checked").length;
                        if (allCbxCnt == selCbxCnt)
                        {
                            trgtObj.find(".checkboxAll").prop("checked", true);
                        }
                        else
                        {
                            trgtObj.find(".checkboxAll").prop("checked", false);
                        }
                    }
                }
            }
        },
        immediately : function() {
            //리스트 조회
            // search();

            //폼 데이터 처리
            // cmmCtrl.setFormData($formObj);
            // codeSelect();
            //레이어 팝업에서 호출 할 수 있다.
            if($formObj.find("input[name=srchPartsComLayer]").size() == 0){
                search($formObj.find("input[name=pageIndex]").val());
            }
        }
    };


    ctrl.exec();

    return ctrl;
});

