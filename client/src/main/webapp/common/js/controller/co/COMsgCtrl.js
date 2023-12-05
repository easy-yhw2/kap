var msgCtrl = (function(){

	"use strict";

	var config = {
		confirm : {
			cre : "생성하시겠습니까?",
			ins : "등록하시겠습니까?",
			upd : "수정하시겠습니까?",
			del : "삭제하시겠습니까?",
			sve : "저장하시겠습니까?",
			list : "목록으로 이동 시 입력한 값이 초기화 처리됩니다. \n이동하시겠습니까?",
			pwdUpd : '비밀번호를 변경하시겠습니까?',
			pwdNextUpd : '비밀번호를 다음에 변경하시겠습니까?',
			emailTrans : "이메일로 전체 아이디를 받으시겠습니까?"
		},
		success : {
			cre : "생성되었습니다.",
			ins : "등록되었습니다.",
			upd : "수정되었습니다.",
			del : "삭제되었습니다.",
			sve : "저장되었습니다.",
			apl : "적용되었습니다.",
			exm : "제출되었습니다.",
			co : {
				login : {
					password : {
						change : "비밀번호 변경이 완료되었습니다."
					},
					email : {
						emailTrans : "전송 되었습니다."
					}
				}
			}
		},
		fail : {
			act : "문제가 발생하여 진행이 중단됩니다. 잠시 후 다시 시도 바랍니다.",
			target : "대상을 선택하세요.",
			notFileRequired: "첨부파일을 등록해주세요.",
			http : {
				status : {
					"C401" : "세션이 종료되었습니다. 다시 로그인 해주세요.",
					"C403" : "권한이 없습니다.", 
					"C406" : "해당 IP로는 접근이 불가합니다.\n관리자에게 문의해주세요."
				}
			},
			del : "삭제할 게시물을 선택하세요.",
			co : {
				login : {
					notExist : "사용자 정보를 찾을 수 없습니다.",
					block : "해당 계정은 비활성화된 계정입니다.\n관리자에게 문의해주세요.",
					notMatch : "입력한 정보를 다시 확인해주세요.\n비밀번호 5회 이상 오류 시 로그인이 제한됩니다.",
					countExcess : "비밀번호 5회 실패로 로그인 제한되었습니다.\n관리자에게 문의해주세요.",
					password : {
						temporary : "임시 비밀번호로 로그인하여 비밀번호를 재설정 합니다.",
						changeCycle : "비밀번호 최종변경일자가 90일이 경과되어 비밀번호 변경 페이지로 이동합니다.",
						includeId : "아이디와 동일한 값은 입력할 수 없습니다.",
						sameString : "동일한 문자/숫자를 3자 이상 연속으로 사용 불가합니다.",
						contString : "4자리 이상 연속된 문자/숫자는 사용 불가합니다.",
						confirm : "비밀번호를 변경하시겠습니까?.",
						notSamePwd : "비밀번호가 일치하지 않습니다.",
						previous_notMatch : "기존비밀번호가 일치하지 않습니다."
					},
					find : {
						year : "년도를 정확히 입력하세요.",
						month : "월을 정확히 입력하세요.",
						day : "일을 정확히 입력하세요.",
						hp : "휴대폰번호를 정확히 입력하세요.",
						email : "이메일을 정확히 입력하세요.",

					}
				},
			}
		}
	};

	var get_message = function(msgCode){
		return eval("config." + msgCode);
	};

	return {
		getMsg : get_message
	}
}());