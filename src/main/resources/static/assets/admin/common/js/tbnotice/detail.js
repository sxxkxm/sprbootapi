import * as constants from "/assets/common/js/constants.js";
import {customAjax} from "/assets/common/js/common.js";

export function adminGetTbnotice(){
	
	customAjax({
		admin: true,
		url: "/api/tbnotice/"+tbnoticeId,
		method: "GET",
		success: (data, status, xhr)=>{
			switch(xhr.status){
	    		case constants.HttpStatusCodes.OK:
	    			let content = data.content;
	    			$('#tbnotice_title').val(data.title);
	    			$('#tbnotice_content').val(data.content);
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