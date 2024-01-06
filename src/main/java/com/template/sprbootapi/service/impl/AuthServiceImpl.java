package com.template.sprbootapi.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.template.sprbootapi.data.constants.ExternalProperties;
import com.template.sprbootapi.data.dto.JwtTokenDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserLoginDto;
import com.template.sprbootapi.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

//	private final RefreshTokenRepository refreshTokenRepository;
	private final ExternalProperties externalProperties;
	
	public AuthServiceImpl(
//			RefreshTokenRepository refreshTokenRepository, 
			ExternalProperties externalProperties) {
//		this.refreshTokenRepository = refreshTokenRepository;
		this.externalProperties = externalProperties;
	}
	
	@Override
	public Algorithm getTokenAlgorithm() {
		return Algorithm.HMAC512(externalProperties.getTokenSecretKey());
	}
	
	/**
	 * 	Access Token 생성을 위한 함수.
	 *  Payload에 tbuser Id를 담는다. 
	 *  
	 *  @param String tbuserId
	 *  @return String accessToken 
	 */
	@Override
	public String createAccessToken(String tbuserId) {
    	return JWT.create()
 			 	  .withSubject("accessToken")
 			 	  .withClaim("id", tbuserId)
 			 	  .withExpiresAt(new Date(System.currentTimeMillis()+externalProperties.getAccessTokenExpirationTime()))
 			 	  .sign(getTokenAlgorithm());
	}

    /**
	 * 	Access Token 검증을 위한 함수
	 *   
	 *  @param String accessToken
	 *  @return String tbuser Id 
	 *  @throws JWTVerificationException(토큰 검증 실패)
	 */
	@Override
	public String verifyAccessToken(String accessToken) throws JWTVerificationException {
		return JWT.require(getTokenAlgorithm())
				  .build()
				  .verify(accessToken)
				  .getClaim("id").asString();
	}

    /**
	 * 	Refresh Token 생성을 위한 함수.
	 *  Payload에 tbuser Id를 담는다.
	 *  Redis에 tbuser Id를 key로, 발급한 Refresh Token을 value로 저장한다.    
	 *  
	 *  @param String tbuserId
	 *  @return String refreshToken 
	 */
	@Override
	public String createRefreshToken(String tbuserId) {
    	String refreshToken = JWT.create()
			     				 .withSubject("refreshToken")
			     				 .withClaim("id", tbuserId)
			     				 .withExpiresAt(new Date(System.currentTimeMillis()+externalProperties.getRefreshTokenExpirationTime()))
			     				 .sign(getTokenAlgorithm());
// 레디스 임시 제거
//		// Redis에 토큰 저장
//		RefreshToken refreshTokenEntity = RefreshToken.builder()
//											  		  .tbuserId(tbuserId)
//											  		  .refreshToken(refreshToken)
//											  		  .build();
//		refreshTokenRepository.save(refreshTokenEntity);
// 레디스 임시 제거
		return refreshToken;
	}

    /**
	 * 	Refresh Token 검증을 위한 함수
	 *   
	 *  @param String refreshToken
	 *  @return String tbuser Id 
	 *  @throws JWTVerificationException(토큰 검증 실패)
	 */
	@Override
	public String verifyRefreshToken(String refreshToken) throws JWTVerificationException {
		return JWT.require(getTokenAlgorithm())
				  .build()
				  .verify(refreshToken)
				  .getClaim("id").asString();
	}
	
	/**
	 * 	Access Token 발급을 위한 함수.
	 *  Refresh Token이 유효하다면 Access Token 발급.
	 *  
	 *  @param String refreshToken
	 *  @return JwtTokenDto(String accessToken, String refreshToken)
	 *  @throws JWTVerificationException(토큰 검증 실패)
	 */
	@Override
	public JwtTokenDto issueAccessToken(String refreshToken) throws JWTVerificationException {
		// Refresh Token 검증(실패시 throws JWTVerificationException)
		System.out.println("refresh : " +refreshToken);
		String tbuserId = verifyRefreshToken(refreshToken);
		// Redis에 해당 tbuser Id로 Refresh Token 있는지 검사 (없으면 throws JWTVerificationException)
// 레디스 임시 제거
//		refreshTokenRepository.findById(tbuserId).orElseThrow(new Supplier<JWTVerificationException>() {
//			@Override
//			public JWTVerificationException get() {
//				return new JWTVerificationException("refresh token not found in database");
//			}
//		});
// 레디스 임시 제거
		// Access Token 생성
		String accessToken = createAccessToken(tbuserId);
		
		return JwtTokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

	// 레디스 임시 제거
	
	/**
	 * 	Refresh Token 삭제를 위한 함수.
	 *  Refresh Token이 유효하다면 Access Token 삭제.
	 *  
	 *  @param String refreshToken
	 *  @return boolean removed
	 *  @exception JWTVerificationException(토큰 검증 실패)
	 */
//	@Override
//	public boolean removeRefreshToken(String refreshToken) {
//		boolean removed = true;
//		try {
//			String tbuserId = verifyRefreshToken(refreshToken);
//			refreshTokenRepository.deleteById(tbuserId);
//		} catch (JWTVerificationException e) {
//			removed = false;
//		}
//		return removed;
//	}

}