import * as constants from "/assets/common/js/constants.js";
import {customAjax} from "/assets/common/js/common.js";

export function adminInsertTbnotice(){
	
	customAjax({
		admin: true,
		url: "/api/tbnotice",
		method: "POST",
		data: JSON.stringify({
			"title": $('#tbnotice_title').val(),
			"content": $('#tbnotice_content').val()
		}),
		success: (data, status, xhr)=>{
	    	switch(xhr.status){
	    		case constants.HttpStatusCodes.CREATED:
	    			location.href="/admin/tbnotice/detail/"+data.id;
	    			break;
	    		default:
	    			console.log("no matching status code");
	    	}
		},
		error: (data)=>{
			console.log('error data : ', data);
		}
	});

}