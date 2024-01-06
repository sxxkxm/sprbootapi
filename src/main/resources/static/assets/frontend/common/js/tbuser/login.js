import * as constants from "/assets/common/js/constants.js";

export function login(username, password){
	
	$.ajax({
		url: "/api/tbuser/login",
		data: JSON.stringify({
			"username": username,
			"password" : password
		}),
		method: "POST",
		headers: {"Content-Type": "application/json"},
		success: (data, status, xhr)=>{
			switch(xhr.status){
			case constants.HttpStatusCodes.OK:
				location.href="/";
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