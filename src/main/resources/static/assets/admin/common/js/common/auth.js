import * as constants from "/assets/common/js/constants.js";

/** 
 *  액세스 토큰을 저장할 로컬변수. 
 */
export var adminLocalAccessToken = "";

/**
 *  액세스 토큰 요청을 위한 함수.
 *  
 *  최초 요청시 callbackSettings가 null
 *  액세스 토큰 만료시 가지고있는 Refresh Token으로 다시 adminGetAccessToken 함수 호출.
 *  다시 호출 할 때는 callbackSettings를 받아서 새로 발급받은 Access Token을 담은 후 미완료된 요청 재전송.
 *  
 *  Cookie에 Refresh Token이 없거나 Refresh Token이 유효하지 않을 때 로그인 페이지로 연결.
 *  
 */
export function adminGetAccessToken(callbackSettings){
	return new Promise((resolve, reject) => {
		$.ajax({
			url: "/api/auth/access-token",
			method: "POST",
			headers: {"Content-Type": "application/json"},
			cache: false,
			success: (data, status, xhr)=>{
	        	switch(xhr.status){
		    		case constants.HttpStatusCodes.OK:
		    			adminLocalAccessToken = xhr.getResponseHeader("Authorization");
		    			if(callbackSettings != null){
		    				callbackSettings.headers = {"Content-Type": "application/json", "Authorization": adminLocalAccessToken},
	    					$.ajax(callbackSettings);
		    			}else{
		    				resolve();
		    			}
		    			break;
		    		default:
		    			console.log("no matching status code");
		    	}
	        	
			},
			error: (data)=>{
	        	switch(data.status){
		    		case constants.HttpStatusCodes.UNAUTHORIZED:
		    			location.href="/admin/tbuser/login";
		    			break;
		    		default:
		    			console.log("no matching status code");
		    	}
	        	reject();
			},
		});
	});
}

/**
 *  현재 로그인 되어있는 유저가 특정 권한을 가지고 있는지 확인하는 함수.
 *  
 *  TODO: customAjax 만든 후 적용 
 */
export function adminCheckAuth(roleType){
	return new Promise((resolve, reject) => {
		$.ajax({
			url: "/api/auth/role-type/"+roleType,
			method: "POST",
			cache: false,
			headers: {"Content-Type": "application/json", "Authorization": adminLocalAccessToken},
			success: (data, status, xhr)=>{
	        	switch(xhr.status){
		    		case constants.HttpStatusCodes.OK:
		    			break;
		    		default:
		    			console.log("no matching status code");
		    	}
	        	resolve();
			},
			error: (data)=>{
	        	switch(data.status){
		    		case constants.HttpStatusCodes.FORBIDDEN:
		    			location.href="/admin/tbuser/login";
		    			break;
		    		default:
		    			console.log("no matching status code");
		    	}
	        	reject();
			},
		});		
	});
}

/**
 *  로그아웃 함수.
 *  
 */
export function adminLogout(){
	$.ajax({
		url: "/api/auth/refresh-token",
		method: "DELETE",
		cache: false,
		headers: {"Content-Type": "application/json"},
		success: (data, status, xhr)=>{
        	switch(xhr.status){
	    		case constants.HttpStatusCodes.OK:
	    			location.reload();
	    			break;
	    		default:
	    			location.href="/admin/tbuser/login";
	    	}
		},
		error: (data)=>{
			location.href="/admin/tbuser/login";
		},
	});
}