var msgCtrl = (function(){

	"use strict";

	var config = {
		confirm : {
			cre : "생성하시겠습니까?",
			ins : "등록하시겠습니까?",
			upd : "수정하시겠습니까?",
			del : "삭제하시겠습니까?",
			sve : "저장하시겠습니까?",
			userReg : "위 정보로 회원가입하시겠습니까??",
			wthdrw : "탈퇴하시겠습니까?",
			cancel : "취소하시겠습니까?",
			cancelInit : "현재 입력된 정보는 초기화됩니다. 계속하시겠습니까?",
			upd2 : "변경하시겠습니까?",
			list : "목록으로 이동 시 입력한 값이 초기화 처리됩니다. \n이동하시겠습니까?",
			pwdUpd : '비밀번호를 변경하시겠습니까?',
			pwdNextUpd : '비밀번호를 다음에 변경하시겠습니까?',
			emailTrans : "이메일로 전체 아이디를 받으시겠습니까?",
			//SQ평가원신청
			sqCertiApply : "SQ평가원 자격증을 신청하시겠습니까?",
			backChk : "이전으로 돌아가시면 현재 입력된 정보는 초기화됩니다\n" + "계속하시겠습니까?",
			cancleBtn : "현재 입력된 정보는 초기화됩니다. 계속하시겠습니까?\n",
			//메인 뉴스레터 신청
			newsletterBtn : "뉴스레터를 신청하시겠습니까?",
		},
		success : {
			cre : "생성되었습니다.",
			ins : "등록되었습니다.",
			upd : "수정되었습니다.",
			del : "삭제되었습니다.",
			upd2: "변경되었습니다.",
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
			},
			mp : {
				join : {
				  emailAuth : '인증이 발송 되었습니다.',
				},
				wthdrw : {
					al_001 : '회원탈퇴가 완료되었습니다.',
				}
			},
			sqCertiApply : "자격증 신청이 완료되었습니다.",
			newsletterSuc : "구독신청이 완료되었습니다.",
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
					wthdrwUser : "존재하지 않는 아이디입니다.",
					notMatch : "아이디/비밀번호가 일치하지 않습니다. 연속 5회 이상 오류 시 이용이 제한됩니다.",
					countExcess : "로그인을 5회 이상 실패하여 이용이 제한됩니다. \n비밀번호 찾기를 통해 비밀번호를 재설정해주세요.",
					notIdExist : "존재하지 않는 계정입니다.",
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
			},
			eb : {
				input : {
					al_003 : '신청사유를 입력해주세요.' ,
					al_004 : '신청분야를 입력해주세요.' ,
					al_005 : '신청주제를 입력해주세요.' ,
					al_006 : '교육 희망일을 선택해주세요.',
					al_007 : '교육장소를 입력해주세요.' ,
					al_008 : '참석대상을 입력해주세요.',
					al_009 : '교육인원을 입력해주세요.' ,
					al_010 : '교육시간을 선택해주세요.',
					al_011 : '회사소개서를 첨부해주세요',
				}
			},
			mp : {
				agree : {
					al_004 : '이용약관에 동의해주세요.' ,
					al_005 : '개인정보처리방침에 동의해주세요.' ,
				},
				join : {
					al_006 : '아이디를 입력해주세요.',
					al_007 : '이미 사용 중인 아이디입니다.' ,
					al_009 : '이메일을 입력해주세요.',
					al_012 : '아이디 중복확인을 진행해주세요.' ,
					al_013 : '비밀번호를 입력해주세요.',
					al_014 : '비밀번호 확인을 입력해주세요.',
					al_015 : '이메일 인증을 진행해주세요',
					al_016 : '주소를 입력해주세요.',
					al_017 : '이미 사용중인 이메일입니다.' ,
					al_018 : '사업자등록번호를 입력해주세요.',
					al_019 : '올바르지 않은 사업자등록번호입니다.',
					al_023 : '이메일 주소를 확인해주세요.',
					al_024 : '인증시간이 초과하였습니다. 다시 인증 해주세요.',
					al_025 : '이메일로 발송된 인증번호를 입력해주세요.',
					al_026 : 'SQ정보는 최대 3개 까지 가능합니다.',
					al_027 : '구분을 선택해주세요.',
					al_028 : '규모를 선택해주세요.',
					al_029 : '설립일자를 선택해주세요.',
					al_030 : '회사전화번호를 입력해주세요.',
					al_031 : '주소를 입력해주세요.',
					al_032 : '부서를 입력해주세요.',
					al_034 : '직급을 입력해주세요.',
					al_035 : '직급 상세를 입력해주세요.',
					al_036 : '사업자등록번호를 인증해주세요.',
					al_037 : '대표자명을 입력해주세요.',
					al_038 : '5~12자 이내 영문 또는 영문+숫자 조합으로 입력해주세요.',
					al_039 : '인증번호가 올바르지 않습니다.',
				},
				mph : {
					al_002 : '비밀번호가 일치하지 않습니다.'
				},
				mpi : {
					al_003 : '회원 탈퇴 기타 사유를 입력해주세요.'
				},
				mpf : {
					al_005 : '지도부품사 선택 또는 기타출장지를 입력해주세요.',
					al_007 : '지도부품사를 선택 후 첨부파일을 등록해주세요.'
				}
			}
			,sqCertiFile : "신청자 증명사진을 등록해주세요."
			,file: {
				extn: "등록가능한 확장자가 아닙니다.",
				length: "첨부하는 파일명의 길이는 20자로 제한합니다.",
				size: "첨부파일은 최대 100M까지만 등록가능합니다.",
				no_size:"비어있는 파일은 등록할 수 없습니다.",
			}
			,newsletter : {
				agree : "소식 및 관련 정보 수신을 동의해주세요.",
				notEmail : "이메일을 입력하세요.",
				emailCheck : "이메일 형태를 확인해주세요.",
				registeredEmail : "이미 구독중인 이메일주소입니다."
			}
			,im : {
				ima : {
					al_001 : "문의가 정상적으로 등록되었습니다."
					, al_002 : "문의유형을 선택해주세요."
					, al_003 : "제목을 입력해주세요."
					, al_004 : "내용을 입력해주세요."
					, al_005 : "첨부 가능한 파일 확장자가 아닙니다."
					, al_006 : "첨부파일 용량은 최대 5MB까지만 등록 가능합니다."
					, al_007 : "첨부파일은 최대 5개까지만 등록 가능합니다."
				}
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