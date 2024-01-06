package com.template.sprbootapi.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.sprbootapi.auth.PrincipalDetails;
import com.template.sprbootapi.data.constants.ExternalProperties;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserLoginDto;
import com.template.sprbootapi.service.AuthService;
import com.template.sprbootapi.util.CookieUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final ObjectMapper objectMapper;
	private final AuthService authService;
	private final ExternalProperties externalProperties;
	
    /**
     *  로그인하려는 사용자의 자격을 확인해 토큰을 발급하는 함수.
	 *  "/api/tbuser/login" 으로 들어오는 요청에 실행된다.
	 *  생성된 Authentication이 SecurityContextHolder에 등록되어 권한처리가 가능하게 한다.
	 *  
	 *  @param HttpServletRequest request
	 *  @param HttpServletResponse response
	 *  @throws AuthenticationException
	 
	@Transactional
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication authentication = null;
		TbuserLoginDto tbuserLoginDto = null; 
		try {
			tbuserLoginDto = objectMapper.readValue(request.getInputStream(), TbuserLoginDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tbuserLoginDto.getEmail(), tbuserLoginDto.getPassword());
		authentication = authenticationManager.authenticate(authenticationToken);
		return authentication;
	} 
	*/
	
	/**
     *  로그인하려는 사용자의 자격을 확인해 토큰을 발급하는 함수.
	 *  "/api/tbuser/login" 으로 들어오는 요청에 실행된다.
	 *  생성된 Authentication이 SecurityContextHolder에 등록되어 권한처리가 가능하게 한다.
	 *  
	 *  @param HttpServletRequest request
	 *  @param HttpServletResponse response
	 *  @throws AuthenticationException
	 */
	@Transactional
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication authentication = null;
		TbuserLoginDto tbuserLoginDto = null; 
		try {
			tbuserLoginDto = objectMapper.readValue(request.getInputStream(), TbuserLoginDto.class);
		} catch (IOException e) {
			System.out.println("attemptAuthentication : 1. Not Enough Parameters?!");
			//e.printStackTrace();
		}
		
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tbuserLoginDto.getUsername(), tbuserLoginDto.getPassword());
			authentication = authenticationManager.authenticate(authenticationToken);
		} catch (AuthenticationException e) {
//			System.out.println("attemptAuthentication : 2. Not Mathced?!");
			e.printStackTrace();
		}
		
		return authentication;
	} 
	
	
	/**
     *  로그인 완료시 호출되는 함수.
     *  Refresh Token을 발급해 HttpServletRespons의 쿠키에 담는다.
	 *  
	 *  @param HttpServletRequest request
	 *  @param HttpServletResponse response
	 *  @param FilterChain chain
	 *  @param Authentication authResult
	 *  @throws IOException, ServletException 
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		// TbuserId로 리프레시토큰 발급
		String refreshToken = authService.createRefreshToken(principalDetails.getTbuser().getId());
		
		/*
		// 웹 용!! 리프레시 토큰 쿠키에 담아 전달
		*/
		CookieUtil.setCookie(response, externalProperties.getRefreshTokenCookieKey(), refreshToken);
		// 앱을 위해서, 헤더에 담아서 전달?!
//		response.addHeader(externalProperties.getAccessTokenHeaderKey(), externalProperties.getAccessTokenPrefix() + refreshToken);
		
		System.out.println("successfulAuthentication : login success?!");
	}
	
}