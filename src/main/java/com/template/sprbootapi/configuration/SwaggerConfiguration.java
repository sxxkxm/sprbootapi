package com.template.sprbootapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.template.sprbootapi.data.dto.TbuserDto;

//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableWebMvc
//public class SwaggerConfiguration {
//	
//	@Bean
//	Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
////				  .ignoredParameterTypes(TbuserDto.class)
//				  .useDefaultResponseMessages(false)
//				  .apiInfo(apiInfo())
//				  .select()
//				  .apis(RequestHandlerSelectors.basePackage("com.template.sprbootapi.controller"))
//				  .paths(PathSelectors.any())
//				  .build();
//	}
//	
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				  .title("sprbootapi api information")
//				  .description("sprbootapi description")
//				  .version("1.0")
//				  .build();
//	}
//	
//}