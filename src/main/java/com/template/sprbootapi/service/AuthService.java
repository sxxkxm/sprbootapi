package com.template.sprbootapi.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.template.sprbootapi.data.dto.JwtTokenDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserLoginDto;

public interface AuthService {
	
	Algorithm getTokenAlgorithm();
	String createAccessToken(String tbuserId);
	String verifyAccessToken(String accessToken) throws JWTVerificationException;
	String createRefreshToken(String tbuserId);
	String verifyRefreshToken(String refreshToken) throws JWTVerificationException;
	JwtTokenDto issueAccessToken(String refreshToken) throws JWTVerificationException;
	
}