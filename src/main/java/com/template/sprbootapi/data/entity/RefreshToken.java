package com.template.sprbootapi.data.entity;


import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *  RedisHash 어노테이션의 value는 Id 어노테이션이 설정된 변수와 함께 Redis의 키로 저장된다
 *  
 *  ex) RedisHash(value="refreshToken"), tbuserId=12345, refreshToken="리프레시" 일 때
 *      refreshToken:12345=리프레시
 *      
 *  RedisHash 어노테이션의 timeToLive는 레디스에서 유지되는 시간(초)이다.    
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class RefreshToken {
	
	@Id
	private String tbuserId;
	private String refreshToken;
	
}