package com.template.sprbootapi.util;

import org.springframework.stereotype.Component;

import com.template.sprbootapi.data.constants.ExternalProperties;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {
	
	private static ExternalProperties externalProperties;
	
	CookieUtil(ExternalProperties externalProperties){
		CookieUtil.externalProperties = externalProperties;
	}
	
	/**
	 *  Cookie 설정을 통일하기 위한 함수.
	 *  설정이 다르면 중복 이름으로 쿠키가 등록될 수 있다.
	 *  HttpServletResponse에 쿠키를 담는다.
	 *  
	 *  @param HttpServletResponse response
	 *  @param String key
	 *  @param String value
	 */
	public static void setCookie(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
//		cookie.setDomain("localhost");
		cookie.setDomain(externalProperties.getServerName());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 *  Cookie 삭제를 위한 함수.
	 *  maxAge(유효기간)을 0으로 설정하고 addCookie 하면 쿠키가 삭제된다.
	 *  
	 *  @param HttpServletResponse response
	 *  @param String key
	 *  @param String value
	 */	
	public static void deleteCookie(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
//		cookie.setDomain("localhost");
		cookie.setDomain(externalProperties.getServerName());
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

}
