package com.template.sprbootapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.template.sprbootapi.data.constants.ExternalProperties;
import com.template.sprbootapi.data.dto.JwtTokenDto;
import com.template.sprbootapi.service.AuthService;
import com.template.sprbootapi.util.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
	
	private final AuthService authService;
	private final ExternalProperties externalProperties;
	
	public AuthRestController(AuthService authService, ExternalProperties externalProperties) {
		this.authService = authService;
		this.externalProperties = externalProperties;
	}
	
	/**
	 * 	Access Token 발급을 위한 컨트롤러.
	 *  Refresh Token이 쿠키에 있고 해당 토큰이 유효하다면 Access Token 발급.
	 *  
	 *  @param String refresh-token(cookie)
	 *  @return String access-token(header), String refresh-token(cookie)
	 *  @exception JWTVerificationException(Invalid Refresh Token)
	 */
	@PostMapping("/access-token")
	public ResponseEntity<JwtTokenDto> issueAccessToken(HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<JwtTokenDto> responseEntity = null;
		Cookie cookie = WebUtils.getCookie(request, externalProperties.getRefreshTokenCookieKey());
		System.out.println("cookie: " + cookie);
		if(cookie == null || cookie.getValue() == null || cookie.getValue().isEmpty()) {
			// 쿠키에 Refresh Token 없으면 return 401(Unauthorized).
			responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();	
		} else {
			String refreshToken = cookie.getValue();
			try {
				// 쿠키에 Refresh Token 있으면 검증 후 Access token 발급.
				JwtTokenDto jwtTokenDto = authService.issueAccessToken(refreshToken);
				// Access Token 발급 완료.
				// Access Token은 response header에 set.
				response.addHeader(externalProperties.getAccessTokenHeaderKey(), externalProperties.getAccessTokenPrefix() + jwtTokenDto.getAccessToken());
				
				/*
				쿠키 안씀
				*/
				// Refresh Token은 cookie에 set.
				//CookieUtil.setCookie(response, externalProperties.getRefreshTokenCookieKey(), jwtTokenDto.getRefreshToken());
				
				// return 200(OK).
				//responseEntity = ResponseEntity.status(HttpStatus.OK).body(jwtTokenDto);
				responseEntity = ResponseEntity.status(HttpStatus.OK).build();
			} catch (JWTVerificationException e) {
				e.printStackTrace();
				// Invalid Refresh Token
				responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		return responseEntity;
	}
	
	/**
	 * 	Refresh Token을 삭제하는 컨트롤러.
	 *  Refresh Token이 쿠키에 있고 해당 토큰이 유효하다면 해당 토큰 삭제.
	 *  
	 *  @param String refresh-token(cookie)
	 */
	@DeleteMapping("/refresh-token")
	public ResponseEntity<Void> removeRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Void> responseEntity = null;
		Cookie cookie = WebUtils.getCookie(request, externalProperties.getRefreshTokenCookieKey());
		String refreshToken = cookie.getValue();
		if(refreshToken == null || refreshToken.isEmpty()) {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
// 레디스 임시 제거
//		}else if(!authService.removeRefreshToken(refreshToken)) {
//			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			CookieUtil.deleteCookie(response, externalProperties.getRefreshTokenCookieKey(), refreshToken);
			responseEntity = ResponseEntity.status(HttpStatus.OK).build();
		}
		return responseEntity;
	}
	
	
	/**
	 * 	페이지 권한 확인을 위한 컨트롤러
	 *  Refresh Token이 쿠키에 있고 해당 토큰이 유효하다면 해당 토큰 삭제.
	 *  
	 *  @param String refresh-token(cookie)
	 */
	@PostMapping("/role-type/{roleType}")
	public ResponseEntity<Void> checkRoleType(@PathVariable("roleType") String roleType, HttpServletRequest request) {
		ResponseEntity<Void> responseEntity = null;
		
		if(request.isUserInRole(roleType)) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).build();	
		}else {
			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		return responseEntity;
	}	
}