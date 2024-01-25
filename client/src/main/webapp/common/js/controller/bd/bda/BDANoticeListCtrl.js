define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bda/BDANoticeListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $("#infoCard").children("a").length; // 게시물 수
    var pageCnt = 1; // 페이지 카운트
    var page = (chilCnt / 9); // 더보기 페이지

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        location.href = "./list?srchVal=" + $("#srchVal").val();
                    }
                }
            }
        },
        classname : {
            moreBtn : {
                event : {
                    click : function () {
                        pageCnt = pageCnt + 1; // 더보기 누를 때마다 1씩 증가
                        var openCnt = $("#infoCard").find(".open").length // 보이는 게시물
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
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        $formObj.find("input[name=detailsKey]").val($(this).data("detailsKey"));
                        var url = "./view?detailsKey=" + $formObj.find("input[name=detailsKey]").val();

                        if($formObj.find("input[name=srchVal]").val() != null) {
                            location.href = url;
                        } else {
                            location.href = url + "&srchVal=" + $formObj.find("input[name=srchVal]").val();
                        }
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
                $(".cntText").text(openCnt +"/"+ chilCnt);
            }else{
                $(".moreBtn").hide();
            }
        }
    };


    ctrl.exec();

    return ctrl;
});

