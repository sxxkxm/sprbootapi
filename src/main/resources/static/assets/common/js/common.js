import * as constants from "/assets/common/js/constants.js";
import {adminGetAccessToken, adminLocalAccessToken} from "/assets/admin/common/js/common/auth.js";
import {userGetAccessToken, userLocalAccessToken} from "/assets/frontend/common/js/common/auth.js";

/** 
 *  TODO: runcount 정리되면 admin에따라서 유저랑 어드민 각자 잘 되는지 확인 
 */
export function customAjax(ajaxSettings){
	
	ajaxSettings = generateDefaultSettings(ajaxSettings);
	
	//console.log('token', ajaxSettings.headers);
	
	$.ajax({
		url: ajaxSettings.url,
		method: ajaxSettings.method,
		data: ajaxSettings.data,
		rerun: ajaxSettings.rerun,
		cache: ajaxSettings.cache,
		headers: ajaxSettings.headers,
		success: (data, status, xhr)=>{
			ajaxSettings.success(data, status, xhr);
		},
		error: (data)=>{
			accessTokenErrorHandler(data, ajaxSettings);
			ajaxSettings.error(data);
		}		
	});
	
}

/**
 *  Ajax 기본값 설정 
 */
function generateDefaultSettings(ajaxSettings){
	
	let localAccessToken = userLocalAccessToken;
	
	if(!("admin" in ajaxSettings)){
		console.log('in');
		ajaxSettings.admin = false;
	}
	
	if(ajaxSettings.admin){
		localAccessToken = adminLocalAccessToken;
	}
	
	if(!("headers" in ajaxSettings)){
		ajaxSettings.headers = {};
	}
	
	if(!("data" in ajaxSettings)){
		ajaxSettings.data = {};
	}
	
	if(!("Content-Type" in ajaxSettings.headers)){
		Object.assign(ajaxSettings.headers, {"Content-Type": "application/json"});
	}
	
	Object.assign(ajaxSettings.headers, {"Authorization": localAccessToken});
	
	if(!("success" in ajaxSettings)){
		ajaxSettings.success = ()=>{};
	}
	
	if(!("error" in ajaxSettings)){
		ajaxSettings.error = ()=>{};
	}
	
	if(!("cache" in ajaxSettings)){
		ajaxSettings.cache = false;
	}
	
	if(!("rerun" in ajaxSettings)){
		ajaxSettings.rerun = true;
	}
	
	return ajaxSettings;
	
}

/**
 *  Access Token이 만료되었을 때 토큰을 다시 발급받은 후 실행하지 못한 요청 다시 전송.  
 */
function accessTokenErrorHandler(data, ajaxSettings){
	
	if(data.status != constants.HttpStatusCodes.UNAUTHORIZED){
		return;
	}
	
	if(!ajaxSettings.rerun){
		if(ajaxSettings.admin){
			location.href="/admin/tbuser/login";
		}else{
			location.href="/tbuser/login";
		}
	}
	
	ajaxSettings.rerun = false;
	
	if(ajaxSettings.admin){
		adminGetAccessToken(ajaxSettings);
	}else{
		userGetAccessToken(ajaxSettings);
	}
	
}