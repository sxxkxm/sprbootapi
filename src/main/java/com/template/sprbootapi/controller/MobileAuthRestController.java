package com.template.sprbootapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.template.sprbootapi.data.constants.ExternalProperties;
import com.template.sprbootapi.data.dto.JwtTokenDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserCreateDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserLoginDto;
import com.template.sprbootapi.service.AuthService;
import com.template.sprbootapi.util.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth-mobile")
public class MobileAuthRestController {
	
	private final AuthService authService;
	private final ExternalProperties externalProperties;
	
	public MobileAuthRestController(AuthService authService, ExternalProperties externalProperties) {
		this.authService = authService;
		this.externalProperties = externalProperties;
	}
	
	/**
	 * 	로그
	 *  Refresh Token과 Access Token 발급해서 body에 담아 전달.
	 *  
	 *  @return String access-token, String refresh-token
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtTokenDto> login(@Valid @RequestBody TbuserLoginDto tbuerLoginDto, HttpServletResponse response) {
		ResponseEntity<JwtTokenDto> responseEntity = null;
		
//		JwtTokenDto jwtTokenDto = authService.issueRefreshTokenAndAccessToken(tbuerLoginDto);
//		responseEntity = ResponseEntity.status(HttpStatus.OK).body(jwtTokenDto);
		
		return responseEntity;
	}	
	
	/**
	 * 	Access Token 발급을 위한 컨트롤러.
	 *  Refresh Token이 쿠키에 있고 해당 토큰이 유효하다면 Access Token 발급.
	 *  
	 *  @param String refresh-token(header)
	 *  @return String access-token, String refresh-token
	 *  @exception JWTVerificationException(Invalid Refresh Token)
	 */
	@PostMapping("/access-token")
	public ResponseEntity<JwtTokenDto> issueAccessToken(HttpServletRequest request, HttpServletResponse response) {
		
		ResponseEntity<JwtTokenDto> responseEntity = null;
		String refreshToken = request.getHeader(externalProperties.getAccessTokenHeaderKey());
		
		if(refreshToken == null) {
			// Refresh Token 없으면 return 401(Unauthorized).
			responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();	
		} else {
			try {
				// Refresh Token 있으면 검증 후 Access token 발급.
				JwtTokenDto jwtTokenDto = authService.issueAccessToken(refreshToken);
				// Access Token 발급 완료.
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(jwtTokenDto);
			} catch (JWTVerificationException e) {
				e.printStackTrace();
				// Invalid Refresh Token
				responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
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