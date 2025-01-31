define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdf/BDFOrganizationListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // function
    function makeList(listData, typeId, cntId, pageId, moreYn) {
        if(listData.list.length > 0) {
            if(moreYn != 'Y') {
                $("#" + typeId).empty();
            }

            if(listData.totalCount < 1 || listData.totalCount == null) {
                $("#" + cntId).text(0);
            }
            else {
                $("#" + cntId).text(listData.totalCount);
            }

            if((listData.pageIndex * listData.listRowSize) >= listData.totalCount) {
                $("#" + pageId).css("display", "none");
            }
            else {
                $("#" + pageId).css("display", "flex");
                $("#" + pageId + " a span.item-count").text("(" + (listData.pageIndex * listData.listRowSize) >= listData.totalCount ? listData.totalCount : (listData.pageIndex * listData.listRowSize) + "/" + listData.totalCount + ")");
                $("#" + pageId + " a").data("pageIndex", listData.pageIndex);
                $("#" + pageId + " a").data("cmssrCbsnCd", listData.cmssrCbsnCd);
            }

            var filePath = "";

            var value = "";
            listData.list.forEach(function (data) {
                if(data.webPath) {
                    filePath = data.webPath;
                }
                else {
                    filePath = "/common/images/@img-foundation-group-member.png";
                }

                value +=
                    "           <a class=\"list-item btnDetail\" href=\"javascript:\" title=\"팝업 열기\" data-mem-seq=\"" + data.memSeq + "\">\n" +
                    "                <div class=\"bg\">\n" +
                    "                    <img src=\"" + filePath + "\" alt=\"\">\n" +
                    "                </div>\n" +
                    "                <div class=\"txt-box\">\n" +
                    "                    <div class=\"names\">\n" +
                    "                        <p class=\"name f-title3\">" + data.name + "</p>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"labels\">\n" +
                    "                        <p class=\"box-label\"><span>" + data.cmssrCbsnCdNm  + "</span></p>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"btn-wrap\">\n" +
                    "                        <div class=\"btn-text-icon black-circle\"><span>더 알아보기</span></div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </a>";
                $("#" + typeId).append(value);
                value="";
            });
        }
        else {
            value = "";

            if(moreYn != 'Y') {
                $("#" + typeId).empty();
            }
            $("#" + cntId).text(0);
            $("#" + pageId).css("display", "none");
            $("#" + pageId + " a").data("pageIndex", listData.pageIndex);
            $("#" + pageId + " a").data("cmssrCbsnCd", listData.cmssrCbsnCd);

            value =
                "<div class=\"no-data-area\">" +
                "    <div class=\"txt-box\">" +
                "        <p class=\"txt f-body1\">등록된 데이터가 없습니다.</p>" +
                "    </div>" +
                "</div>"

            $("#" + typeId).append(value);
            value="";
        }
    }


    // set model
    ctrl.model = {
        id : {

            goQa : {
                event : {
                    click : function() {
                        var memSeq = $(".memberDetailsPopup").data("detailskey");

                        if(memSeq == "" || memSeq === undefined || memSeq == null){
                            if(confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")) {
                                location.href = $("#goQa").data("url");
                            } else {
                                return false;
                            }
                        }
                        else {
                            location.href = $("#goQa").data("url");
                        }
                    }
                }
            }

        },
        classname : {

            btnFilter : {
                event : {
                    click : function() {
                        var cmssrCbsnCd = $(this).data("cmssrCbsnCd");

                        var typeId;
                        var cntId;
                        var pageId;
                        if(cmssrCbsnCd.charAt(10) == 3) {
                            typeId = "advList";
                            cntId = "advCnt";
                            pageId = "advPage";
                        }
                        else {
                            typeId = "proList";
                            cntId = "proCnt";
                            pageId = "proPage";
                        }

                        var cmssrTypeList = new Array();

                        if(cmssrCbsnCd.length < 12) {
                            cmssrTypeList.push(cmssrCbsnCd);
                            cmssrCbsnCd = null;
                        }
                        else {
                            cmssrTypeList.push(cmssrCbsnCd.substring(0, 11));
                        }

                        $.ajax({
                            type: "post",
                            url: './list',
                            dataType: "json",
                            data: {
                                "cmssrCbsnCd" : cmssrCbsnCd,
                                "cmssrTypeList" : cmssrTypeList
                            },
                            success: function (rtnData) {
                                if(rtnData) {
                                    makeList(rtnData, typeId, cntId, pageId);
                                }
                            },
                            error: function (xhr, ajaxSettings, thrownError) {
                                alert("조회에 실패했습니다.");
                            }
                        });
                    }
                }
            },

            btnMore : {
                event : {
                    click : function() {
                        var pageIndex = $(this).data("pageIndex");
                        var cmssrCbsnCd = $(this).data("cmssrCbsnCd");
                        var cmssrTypeCd = $(this).data("cmssrTypeCd");

                        var typeId;
                        var cntId;
                        var pageId;
                        if(cmssrTypeCd.charAt(10) == 3) {
                            typeId = "advList";
                            cntId = "advCnt";
                            pageId = "advPage";
                        }
                        else {
                            typeId = "proList";
                            cntId = "proCnt";
                            pageId = "proPage";
                        }

                        if($("#" + cntId).text() <= pageIndex) {
                            return false;
                        }

                        var cmssrTypeList = new Array();
                        cmssrTypeList.push(cmssrTypeCd);

                        $.ajax({
                            type: "post",
                            url: './list',
                            dataType: "json",
                            data: {
                                "pageIndex" : pageIndex + 1,
                                "cmssrTypeList" : cmssrTypeList,
                                "cmssrCbsnCd" : cmssrCbsnCd
                            },
                            success: function (rtnData) {
                                if(rtnData) {
                                    makeList(rtnData, typeId, cntId, pageId, 'Y');
                                }
                            },
                            error: function (xhr, ajaxSettings, thrownError) {
                                alert("조회에 실패했습니다.");
                            }
                        });
                    }
                }
            },

            btnDetail : {
                event : {
                    click : function() {
                        var detailsKey = $(this).data("memSeq");

                        $.ajax({
                            type: "post",
                            url: './select',
                            dataType: "json",
                            data: {
                                "detailsKey" : detailsKey
                            },
                            success: function (rtnData) {
                                if(rtnData) {
                                    var cmssrType = "";
                                    if(rtnData.cmssrCbsnCd.charAt(10) == 3) {
                                        cmssrType = "자문위원";
                                    }
                                    else {
                                        cmssrType = "전문위원";
                                    }


                                    $(".memberDetailsPopup #cmssrType").text(cmssrType);
                                    $(".memberDetailsPopup #name").text(rtnData.name);
                                    $(".memberDetailsPopup #email").text(rtnData.email);
                                    $(".memberDetailsPopup #cmssrCbsnCdNm span").text(rtnData.cmssrCbsnCdNm);
                                    if (rtnData.cmssrMjrCarerExpsYn == 'Y'){
                                        $(".memberDetailsPopup #cmssrMjrCarerCntn").html(rtnData.cmssrMjrCarerCntn.replaceAll(/(\n|\r\n)/g, "<br>"));
                                        if(rtnData.cmssrCnstgFldCntn) {
                                            $(".memberDetailsPopup #cmssrCnstgFldCntn").html(rtnData.cmssrCnstgFldCntn.replaceAll(/(\n|\r\n)/g, "<br>"));
                                        }
                                        else {
                                            $(".memberDetailsPopup #cmssrCnstgFldCntn").text("-");
                                        }
                                    }else{
                                        $(".memberDetailsPopup #cmssrMjrCarerCntn").text("-");
                                        $(".memberDetailsPopup #cmssrCnstgFldCntn").text("-");
                                    }
                                    // $(".memberDetailsPopup #goQa span").text("/foundation/cs/qa/index?inqSec=" + rtnData.cmssrCbsnCdNm);
                                    $(".memberDetailsPopup #goQa").attr("data-url", "/foundation/cs/qa/index?inqSec=" + rtnData.cmssrCbsnCdNm);

                                    openPopup('memberDetailsPopup');
                                    // $(".memberDetailsPopup").css("display", "block");
                                    // $(".dimd").css("display", "block");
                                }
                            },
                            error: function (xhr, ajaxSettings, thrownError) {
                                alert("조회에 실패했습니다.");
                            }
                        });
                    }
                }
            },

            /*btnClose : {
                event : {
                    click : function() {
                        $(".memberDetailsPopup").css("display", "none");
                        $(".dimd").css("display", "none");
                    }
                }
            },*/

        },
        immediately : function() {

        }
    };


    ctrl.exec();

    return ctrl;
});

