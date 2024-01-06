package com.template.sprbootapi.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class ControllerLogAspect {
	
	ObjectMapper objectMapper;
	
	private final Logger LOGGER = LoggerFactory.getLogger(ControllerLogAspect.class);
	
	public ControllerLogAspect(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	/**
	 * 	로그 설정할 Pointcut.
	 */
    @Pointcut("execution(* com.template.sprbootapi.controller..*.*(..))"
//    		+ "execution(* com.template.sprbootapi.service..*.*(..)) || "
//    		+ "execution(* com.template.sprbootapi.data.dao..*.*(..)) || "
//    		+ "execution(* com.template.sprbootapi.data.dto..*.*(..)) || "
//    		+ "execution(* com.template.sprbootapi.data.repository..*.*(..)) || "
//    		+ "execution(* com.template.sprbootapi.data.entity..*.*(..))"
    		)
    private void pointCut(){}

    /**
	 *  Pointcut 이전에 실행되는 로그 출력 함수.
	 *  Jackson으로 json 파싱해서 로그 출력.
	 *  
	 *  todo - JsonProcessingException printStackTrace했을 때 특정 요청 시 예외 발생 처리하기
	 *  
	 *  @param JoinPoint joinPoint
	 *  @exception JsonProcessingException
	 */
    @Before("pointCut()")
    public void beforeLog(JoinPoint joinPoint) {
    	String paramString = "";
    	Map<String, Object> params = getParams(joinPoint);
    	
    	if(params.size() <= 0) {
    		paramString = " : " + "no parameter";
    	}else {
    		try {
    			paramString = "\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
    		} catch (JsonProcessingException e) {}
    	}
    	
    	LOGGER.info(joinPoint.getSignature().getDeclaringTypeName() + " - " + joinPoint.getSignature().getName() + paramString);
    }

    /**
	 *  Pointcut 이후에 실행되는 로그 출력 함수.
	 *  Jackson으로 json 파싱해서 로그 출력.
	 *  
	 *  @param JoinPoint joinPoint
	 *  @param Object returnObj
	 *  @exception JsonProcessingException
	 */
    @AfterReturning(value = "pointCut()", returning = "returnObj")
    public void afterLog(JoinPoint joinPoint, Object returnObj) {
    	try {
			LOGGER.info(joinPoint.getSignature().getDeclaringTypeName() + " - " + joinPoint.getSignature().getName() + " return : \n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(returnObj));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 *  JoinPoint에 담겨있는 파라미터를 Map에 담아 리턴하는 함수.
	 *  
	 *  @param JoinPoint joinPoint
	 *  @exception Map<String, Object> params
	 */
    private Map<String, Object> getParams(JoinPoint joinPoint) {
    	CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    	String[] parameterNames = codeSignature.getParameterNames();
    	Object[] args = joinPoint.getArgs();
    	Map<String, Object> params = new HashMap<>();
    	for (int i = 0; i < parameterNames.length; i++) {
    		params.put(parameterNames[i], args[i]);
    	}
    	return params;
    }
    
}