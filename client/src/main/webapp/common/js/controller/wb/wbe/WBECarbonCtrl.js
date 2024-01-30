define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbe/WBECarbonCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var addCount = 3;
    var imageText = "";

    // set model
    ctrl.model = {
        id : {
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(500,600,"zipCode","bscAddr","dtlAddr");
                    }
                }
            },
            sameAsHQChk : {
                event : {
                    change : function() {
                        if($(this).is(':checked')){
                            var oriZipCode = $("#oriZipCode").val();
                            var oriBscAddr = $("#oriBscAddr").val();
                            var oriDtlAddr = $("#oriDtlAddr").val();

                            $("#zipCode").val(oriZipCode);
                            $("#bscAddr").val(oriBscAddr);
                            $("#dtlAddr").val(oriDtlAddr);
                        }else{
                            $("#zipCode").val('');
                            $("#bscAddr").val('');
                            $("#dtlAddr").val('');
                        }
                    }
                }
            },
            nextBtn : {
                event : {
                    click : function() {
                        var episdSeq = $(this).data("episdSeq");
                        if(confirm("매출액 등이 최신 정보여야 합니다.\n현재 정보로 신청하시겠습니까?")) {
                            location.href = "./step2?episdSeq="+episdSeq;
                        }
                    }
                }
            }
        },
        classname : {
            addMore : {
                event : {
                    click : function() {

                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리
                            $('.divide').append(respObj);
                            addCount = 3+addCount;
                            //전체 갯수
                            var totalCnt = $('#totalCnt').text();

                            if (addCount >= totalCnt) {
                                $('.add-load').hide();
                            } else {
                                $('#firstIndex').val(addCount);
                                $('.item-count').text("("+ addCount + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "GET", "html");
                    }
                }
            },
            apply : {
                event : {
                    click : function() {
                        var episdSeq = $(this).data("episd");
                        var param = {
                            episdSeq : episdSeq
                        };

                        //신청페이지 로직점검
                        cmmCtrl.paramAjax(function(data){
                            if (data.resultCode == 999) {
                                if (confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")) {
                                    location.href = "/login?rtnUrl="+encodeURIComponent(window.location.pathname);
                                }
                            } else if (data.resultCode == 100) {
                                alert('해당 사업은 부품사 회원만 신청 가능합니다.');
                            } else if (data.resultCode == 150) {
                                alert('위원회원은 해당 서비스를 이용할 수 없습니다.');
                            } else if (data.resultCode == 190) {
                                alert('1,2차 부품사만 신청가능합니다.');
                            } else if (data.resultCode == 300) {
                                if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                    location.href = "/my-page/coexistence/list";
                                }
                            } else if (data.resultCode == 200) {
                                location.href = "./step1?episdSeq="+episdSeq;
                            }
                        },"./applyChecked",param, "json", false, false, "get");
                    }
                }
            },
            insert : {
                event : {
                    click : function() {

                        if($("#zipCode").val() == "" || $("#bscAddr").val() == ""){
                            alert("구축사업장 주소를 입력해주세요.");
                            return;
                        }

                        if($("#dtlAddr").val() == ""){
                            alert("구축사업장 상세 주소를 입력해주세요.");
                            return;
                        }

                        if($("#searchFile").val() == "") {
                            alert("신청서류를 모두 등록해주세요.");
                            return;
                        }

                        //이용약관 체크여부
                        if ($('#agreeChk').is(':checked')) {
                            if (confirm("위 정보로 사업을 신청하시겠습니까??")) {
                                cmmCtrl.fileFrmAjax(function (data) {
                                    //콜백함수. 페이지 이동
                                    location.replace("./complete");
                                }, "./insert", $formObj, "json");
                            }
                        } else {
                            alert('약관에 동의해주세요.')
                        }
                    }
                }
            }
        },
        immediately : function(){
            $formObj.find("input[type=file]").fileUpload({
                loading:false,
                sync:true
            },function(data){
                console.log(data);
                //해당 input file 객체에 data(tempFileData) 응답 값이 저장
                $('.empty-txt').text(data[0].orgnFileNm);
            });
            $('#firstIndex').val(addCount);
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

