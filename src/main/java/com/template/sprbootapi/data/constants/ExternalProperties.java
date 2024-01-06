package com.template.sprbootapi.data.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ExternalProperties {
	
	@Value("${external.jwt.tokenSecretKey}")
	private String tokenSecretKey;
	
	@Value("${external.jwt.accessTokenPrefix}")
	private String accessTokenPrefix;
	
	@Value("${external.jwt.accessTokenExpirationTime}")
	private Integer accessTokenExpirationTime;
	
	@Value("${external.jwt.headerKey}")
	private String accessTokenHeaderKey;
	
	@Value("${external.jwt.cookieKey}")
	private String refreshTokenCookieKey;
	
	@Value("${external.jwt.refreshTokenExpirationTime}")
	private Integer refreshTokenExpirationTime;
	
	@Value("${external.serverName}")
	private String serverName;
	
	@Value("${external.serverPort}")
	private String serverPort;

}
