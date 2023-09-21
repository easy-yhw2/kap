define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/sm/smc/SMCPopListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");

    // set model
    ctrl.model = {
        id : {
            btnSearch : {
                event : {
                    click : function() {
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            },
            listRowSize : {
                event : {
                    change : function(){
                        search(1);
                    }
                }
            },
            btn_delete : {
                event: {
                    click: function () {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        if(confirm("삭제 처리하겠습니끼?"))
                        {
                            //삭제 전송
                            cmmCtrl.frmAjax(function(respObj){
                                if(respObj != undefined && respObj.respCnt > 0){
                                    var msg = "삭제되었습니다.";

                                    alert(msg);
                                    $formObj.find("#btnSearch").click();
                                }
                                else{
                                    alert(msgCtrl.getMsg("fail.act"));
                                }
                            }, "./delete", frmDataObj, "POST", "json");
                        }

                    }
                }
            },
            btnSort : {
                event : {
                    click : function () {
                        console.log("TEST");
                        var btn = $(this),
                            td = btn.parents("td"),
                            key = td.data("key"),
                            sort = td.data("value"),
                            index = btn.parent().index(),
                            sortType = btn.attr('name') === 'sortUp' ? 'UP' : 'DOWN';

                        console.log("td:" + td + "," +
                            "key:" + key + "," +
                            "sort:" + sort + "," +
                            "index:" + index + "," +
                            "sortType:" + sortType
                        )

                        if(sortType != null) {

                            //if(sortType == 'UP' && $("pageIndex").val() == '1' && btn.parents('tr').prev().length == 0) {
                            if(sortType == 'UP' && btn.parents('tr').prev().length == 0) {
                                return false;
                                //} else if (sortType == 'DOWN' && $("pageIndex").val() == $(".pagination").children().length && btn.parents('tr').next().length == 0) {
                            } else if (sortType == 'DOWN' && btn.parents('tr').next().length == 0) {
                                return false;
                            }

                            var ajaxData = {
                                seq : key,
                                ord : sort,
                                sortType : sortType
                            }

                            console.log(JSON.stringify(ajaxData, null, 2));

                            $("#frmSearch").serializeArray().forEach(function(field) {
                                ajaxData[field.name] = field.value;
                            });

                            $.ajax({
                                type: "post",
                                url: "./sort",
                                dataType: "json",
                                data: ajaxData,
                                success: function(r) {
                                    alert('노출 순서가 변경되었습니다!');

                                    cmmCtrl.setFormData($formObj);
                                    search(1);
                                    /*console.log("rewRow값:::" + JSON.stringify(r.newRow));

                                    console.log(r.rtnData.list[0]);

                                    var trLength = sortType == 'DOWN' ? btn.parents("tr").prev().length : btn.parents("tr").next().length,
                                        insertNewTr = trLength == 0 ? true : false;

                                    alert("insertNewTr::::::::::::" + insertNewTr);
                                    if(insertNewTr) {
                                        var tr = btn.parents("tr"),
                                            newTr = r.newRow;

                                        var trHTML = "<tr><td class='text-center'><label class='checkbox-inline c-checkbox'>";
                                        trHTML += "<input type='checkbox' value='" + newTr.seq + "'class='checkbox_single notRequired' />";
                                        trHTML += "<span class='ion-checkmark-round'></span></label></td>";
                                        trHTML += "<td class='text-center'>" + tr.children()[1].textContent + "</td>";

                                        newTr.titlStr = newTr.titl;
                                        if(newTr.titl.length > 35) {
                                            newTr.titlStr = newTr.titl.substring(0, 35) + '...';
                                        }

                                        //var aStr = "commonCtrl.setViewPage(\"detailsKey\", \"" + newTr.popSeq+"\",\"./write\")";
                                        trHTML+= "<td><a href='javascript: onclick='./write'>" + newTr.titlStr + "</a></td>";
                                        trHTML+= "<td>"+(newTr.useYn=='Y'?'노출':'미노출')+"</td>"; trHTML+="<td>"+ (newTr.odtmYn == 'Y' ? '항시노출' : (newTr.strtDtm).substr(0,16)+"~"+(newTr.endDtm).substr(0,16)) + "</td>";
                                        trHTML+= "<td>"+(newTr.regDtm).substr(0,16)+"</td>";
                                        trHTML+= "<td data-key'" + newTr.seq +"' data-value='" + sort +"'>";
                                        trHTML+= "<button type='button' class='btn btn-default btn-xs sortUp' name='sortUp'><i class='ion-arrow-up-b'></i></button>";
                                        trHTML+= "<button type='button' class='btn btn-default btn-xs ml-sm sortDown' name='sortDown'><i class='ion-arrow-down-b'></i></button></td></tr>";
                                        alert(trHTML);
                                        tr.after(trHtml);
                                        //tr.next().find('#sortUp').bind('click', ctrl.sort);
                                        //tr.next().find('#sortDown').bind('click', ctrl.sort);

                                        tr.remove();

                                    } else {

                                    }*/
                                }
                            })
                        }
                    }
                }
            },
            btnUseYn : {
                event : {
                    click : function() {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        alert('미노출 처리할 행 개수:::::' + delActCnt);
                        if(confirm("미노출 처리하겠습니끼?"))
                        {
                            //삭제 전송
                            cmmCtrl.frmAjax(function(respObj){
                                if(respObj != undefined && respObj.respCnt > 0){
                                    var msg = "미노출 처리가 완료되었습니다.";

                                    alert(msg);
                                    $formObj.find("#btnSearch").click();
                                }
                                else{
                                    alert(msgCtrl.getMsg("fail.act"));
                                }
                            }, "./use-yn-update", frmDataObj, "POST", "json");
                        }
                    }
                }
            }
        },
        classname : {
            pageSet : {
                event : {
                    click : function() {
                        if( $(this).attr("value") != "null" ){
                            search($(this).attr("value"));
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
            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);

            search($formObj.find("input[name=pageIndex]").val());
        }
    };

    //목록 조회
    var search = function(page){
        //data로 치환해주어야한다.
        //cmmCtrl.setFormData($formObj);
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
        }, "./select", $formObj, "POST", "html");
    }

    ctrl.exec();

    return ctrl;
});