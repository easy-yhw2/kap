define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBSupplyWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    $(".file-list").hide();
    // 파일 체크
    var extnCheck = function(obj, extns, maxSize, maxLengh)
    {
        var fileObj = jQuery(obj).val(), isFile = true;
        var fileId = obj.id;
        var fileLengh = document.querySelectorAll('p.file-name').length;;

        if (!fileObj)
        {
            isFile = false;
        }
        else
        {
            var file;
            file = obj.files[0];

            var fileExtn = file.name.split(".").pop();
            var fileName = file.name.split(".")[0];
            if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                //파일확장자 체크
                $('#'+fileId).val("");
                $('#'+fileId).closest(".form-group").find('.file-list').empty();
                alert('첨부 가능한 파일 확장자가 아닙니다.');

                isFile = false;
            } else {
                //파일용량 체크 파일갯수 체크
                if (typeof obj.files != "undefined")
                {
                    var fileSize = file.size;
                    var maxFileSize = maxSize * 1024 * 1024;
                    if (fileSize > maxFileSize)
                    {
                        $('#'+fileId).val("");
                        $('#'+fileId).closest(".form-group").find('.file-list').empty();
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                        isFile = false;
                    }

                    if(fileLengh == maxLengh){
                        alert("첨부파일은 최대 " + maxLengh + " 개만 등록 가능합니다.");
                        isFile = false;
                    }
                }

            }
            if (isFile) {
                $(".file-list").show();
                var fileHtml = '<p class="file-name"><span class="name">' + fileName + '</span>';
                fileHtml += '<span class="unit">.' + fileExtn + '</span></p>';
                fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button>';

                $('#'+fileId).closest(".form-group").find('.file-list').append(fileHtml);
                // 파일 추가되면 class 추가
                $('#'+fileId).closest(".form-group").find('.file-list-area').addClass('attached');
            }
        }
    };

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
            modify : {
                event : {
                    click : function() {
                        var $formObj = $('#frmData');
                        var file = $formObj.find('input[type=file]');
                        var valid = true;

                        file.each(function(i) {
                            if (!$(this).val()) {
                                alert('신청서류를 모두 등록해주세요.');
                                valid = false;
                                return false;
                            }
                        });

                        if (valid) {
                            cmmCtrl.fileFrm(function(data){
                                if (data.respCnt == 100) {
                                    alert("잘못된 접근입니다. 다시 시도바랍니다.");
                                }
                                location.href = "./list";
                            }, "./update", $formObj, "json");
                        }
                    }
                }
            },
            searchFile : {
                event : {
                    change : function() {
                        extnCheck(this, "jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip", 50, 1);
                    }
                }
            },
            fileDelete : {
                event : {
                    click : function() {
                        $(this).closest(".form-group").find("input[type=file]").val("");
                        $(this).closest(".form-group").find('.file-list').empty();
                    }
                }
            }
        },
        immediately : function(){

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

