define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/cb/cbb/CBBManageConsultIndexCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $("#infoCard").children("a").length; // 위원 카드 수
    var pageCnt = 1; // 페이지 카운트
    var page = (chilCnt/9); // 더보기 페이지
    // set model
    ctrl.model = {
        id : {

        },
        classname : {
            //검색 레이어에서 선택시 호출
            moreBtn : {
                event : {
                    click : function(){
                        pageCnt = pageCnt+1; // 더보기 누를 때마다 1씩 증가
                        var openCnt = $("#infoCard").find(".open").length // 보이는 게시물
                        var closeCnt = $("#infoCard").find(".close").length; // 숨겨진 게시물
                        if(pageCnt <= page){
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).show();
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).removeClass("open");
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).addClass("open");
                            $(".cntText").text(openCnt+9 +"/"+ chilCnt);
                        }else{
                            $("#infoCard").find(".close").show();
                            $(".moreBtn").hide();
                        }
                    }
                }
            },
            techApplication : {
                event : {
                    click : function(e){
                        if(e){
                            if(confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")){
                                location.href="/login?rtnUrl=/consulting/manage/content";
                            }
                        }
                    }
                }
            },
            popOpen : {
                event : {
                    click : function() {
                        var memSeq = $(this).find(".memSeq").val();
                        var cmssrCbsnCdNm = $(this).find(".cmssrCbsnCdNm").text();
                        var cssInfo = {};
                        cssInfo.detailsKey = memSeq;
                        cmmCtrl.jsonAjax(function (data) {
                            var info = JSON.parse(data);
                            $(".cmssrName").text(info.name);
                            $(".email").text(info.email);
                            $(".cmssrCbsnCd").text(cmssrCbsnCdNm);
                            var expsYn = info.cmssrMjrCarerExpsYn;
                            if(expsYn == 'Y'){
                                $(".cmssrMjrCarerCntn").text(info.cmssrMjrCarerCntn);
                            }
                        }, './selectDtlInfo', cssInfo, "text");
                        openPopup('memberDetailsPopup');
                    }
                }
            }
        },
        immediately : function() {
            if(chilCnt > 9){
                $("#infoCard").children("a").slice(9,chilCnt).hide();
                $("#infoCard").children("a").slice(9,chilCnt).removeClass("open");
                $("#infoCard").children("a").slice(9,chilCnt).addClass("close");
                var openCnt = $("#infoCard").find(".open").length // 보이는 게시물
                var closeCnt = $("#infoCard").find(".close").length; // 숨겨진 게시물
                $(".cntText").text(openCnt +"/"+ chilCnt);
            }else{
                $(".moreBtn").hide();
            }

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});