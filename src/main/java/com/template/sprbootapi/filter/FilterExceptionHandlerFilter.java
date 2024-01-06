package com.template.sprbootapi.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FilterExceptionHandlerFilter extends OncePerRequestFilter {
	
	@Autowired
	ObjectMapper objectMapper;
	
	/**
     *  TokenExpiredException 핸들링을 위한 필터
	 *  상태코드 UNAUTHORIZED(401)을 response에 담아 리턴한다
	 *  
	 *  @param HttpServletRequest request
	 *  @param HttpServletResponse response
	 *  @param FilterChain chain
	 *  @throws IOException, ServletException
	 *  @exception TokenExpiredException, IOException
	 */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
//        	System.out.println("filter start!!!");
            filterChain.doFilter(request, response);
        }catch (TokenExpiredException e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try{
                response.getWriter().write("Invalid Access Token");
            }catch (IOException i){
                i.printStackTrace();
            }
        }
    }
	
}