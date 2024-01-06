import * as constants from "/assets/common/js/constants.js";


/**
 *  관리자 로그인을 위한 함수.
 *  
 *  로그인 성공시 200 -> 관리자 메인페이지로 이동
 *  로그인 실패시 401 Unauthorized 
 *  
 */
export function adminLogin(username, password){
	
	$.ajax({
		url: "/api/tbuser/login",
		method: "POST",
		headers: {"Content-Type": "application/json"},
		data: JSON.stringify({
			"username": username,
			"password" : password
		}),
		success: (data, status, xhr)=>{
        	switch(xhr.status){
	    		case constants.HttpStatusCodes.OK:
	    			location.href="/admin";
	    			break;
	    		default:
	    			console.log("no matching status code");
	    	}
		},
		error: (data)=>{
			switch(data.status){
	    		case constants.HttpStatusCodes.UNAUTHORIZED:
	    			alert("일치하는 계정 정보가 없습니다.");
	    			break;
	    		default:
	    			console.log("no matching status code");
	    	}
		}
	});
	
}