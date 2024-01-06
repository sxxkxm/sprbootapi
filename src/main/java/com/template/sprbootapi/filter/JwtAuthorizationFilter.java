package com.template.sprbootapi.filter;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.template.sprbootapi.auth.PrincipalDetails;
import com.template.sprbootapi.data.constants.ExternalProperties;
import com.template.sprbootapi.data.entity.Tbuser;
import com.template.sprbootapi.data.repository.TbuserRepository;
import com.template.sprbootapi.exception.NoMatchingUserException;
import com.template.sprbootapi.service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	private final TbuserRepository tbuserRepository;
	private final AuthService authService;
	private final ExternalProperties externalProperties;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TbuserRepository tbuserRepository, AuthService authService, ExternalProperties externalProperties) {
		super(authenticationManager);
		this.tbuserRepository = tbuserRepository;
		this.authService = authService;
		this.externalProperties = externalProperties;
	}
	
	/**
     *  권한 인가를 위한 함수.
     *  Access Token을 검증하고 유효하면 Authentication을 직접 생성해 SecurityContextHolder에 넣는다.
	 *  
	 *  @param HttpServletRequest request
	 *  @param HttpServletResponse response
	 *  @param FilterChain chain
	 *  @throws IOException, ServletException 
	 *  @exception NoMatchingUserException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String jwtHeader = request.getHeader(externalProperties.getAccessTokenHeaderKey());
		if(jwtHeader == null || !jwtHeader.startsWith(externalProperties.getAccessTokenPrefix())) {
			// 토큰 없을 시 Authentication 없는 상태로 doFilter -> Spring Security가 잡아낸다.
			chain.doFilter(request, response);
			return;
		}
		String accessToken = request.getHeader(externalProperties.getAccessTokenHeaderKey()).replace(externalProperties.getAccessTokenPrefix(), "");
		String tbuserId = authService.verifyAccessToken(accessToken);
		// 유저 조회, 없을 시 return NoMatchingUserException(404)
		Tbuser tbuserEntity = tbuserRepository.findEntityGraphRoleTypeById(tbuserId).orElseThrow(new Supplier<NoMatchingUserException>() {
			@Override
			public NoMatchingUserException get() {
				return new NoMatchingUserException("id : " + tbuserId);
			}
		});
		PrincipalDetails principalDetails = new PrincipalDetails(tbuserEntity);
		// Authentication 생성
		Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
		// SecurityContextHolder에 Authentication을 담아서 Spring Security가 권한 처리 할 수 있게 한다.
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		request.setAttribute("tbuserId", tbuserId);
		
		chain.doFilter(request, response);
	}

}