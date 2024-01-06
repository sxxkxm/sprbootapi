package com.template.sprbootapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.sprbootapi.data.constants.ExternalProperties;
import com.template.sprbootapi.data.repository.TbuserRepository;
import com.template.sprbootapi.filter.FilterExceptionHandlerFilter;
import com.template.sprbootapi.filter.JwtAuthenticationFilter;
import com.template.sprbootapi.filter.JwtAuthorizationFilter;
import com.template.sprbootapi.service.AuthService;

@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
@Configuration
public class SecurityConfiguration {
	
	private final TbuserRepository tbuserRepository;
	private final CorsFilterConfiguration corsFilterConfiguration;
	private final ObjectMapper objectMapper;
	private final AuthService authService;
	private final ExternalProperties externalProperties;
	
	public SecurityConfiguration(TbuserRepository tbuserRepository, CorsFilterConfiguration corsFilterConfiguration, ObjectMapper objectMapper, AuthService authService, ExternalProperties externalProperties) {
		this.tbuserRepository = tbuserRepository;
		this.corsFilterConfiguration = corsFilterConfiguration;
		this.objectMapper = objectMapper;
		this.authService = authService;
		this.externalProperties = externalProperties;
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    /**
	 *  Spring Security 권한 설정.
	 *  
	 *  todo - 어노테이션으로 대체할수있는지 확인
	 *  
	 *  @param HttpSecurity http
	 *  @throws Exception
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf(csrf -> csrf.disable())
					.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
					.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.formLogin(formLogin -> formLogin.disable())
					.httpBasic(httpBasic -> httpBasic.disable())
					.apply(new CustomDsl());
		
		return httpSecurity.build();
	}
					
	public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
		
	    /**
		 *  Jwt Token Authentication을 위한 filter 설정.
		 *  
		 *  jwtAuthenticationFilter: 인증을 위한 필터("/api/tbuser/login")
		 *  JwtAuthorizationFilter: 인가를 위한 필터
		 *  FilterExceptionHandlerFilter: TokenExpiredException 핸들링을 위한 필터 
		 *  
		 *  @param HttpSecurity http
		 *  @throws Exception
		 */
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, objectMapper, authService, externalProperties);
			jwtAuthenticationFilter.setFilterProcessesUrl("/api/tbuser/login");
			
			http.addFilter(corsFilterConfiguration.corsFilter())
				.addFilter(jwtAuthenticationFilter)
				.addFilter(new JwtAuthorizationFilter(authenticationManager, tbuserRepository, authService, externalProperties))
				.addFilterBefore(new FilterExceptionHandlerFilter(), BasicAuthenticationFilter.class);
		}
		
	}

}