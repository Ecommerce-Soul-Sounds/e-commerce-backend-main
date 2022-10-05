package com.revature.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private ObjectMapper om = new ObjectMapper();
	
	@Before(value="execution(* com.revature.controllers.*.*(..))")
	public void logBeforeController(JoinPoint jp) throws JsonProcessingException {
		log.info("Incoming request routed to - [ {} : {} ]", jp.getTarget().getClass().getName(), jp.getSignature().getName());
		if (jp.getArgs().length > 0 && !(jp.getArgs()[0] instanceof org.apache.catalina.session.StandardSessionFacade) ) {
			String requestJSON = om.writeValueAsString(jp.getArgs()[0]);
			log.info("Request body is: {}", requestJSON);
		}
	}
	
	@AfterReturning(value="execution(* com.revature.controllers.*.*(..))", returning = "result")
	public void logAfterController(JoinPoint jp, Object result) {
		log.info("sending response: {}", result);
	}
	
	@Before(value="execution(* com.revature.services.*.*(..))")
	public void logBeforeService(JoinPoint jp) {
		log.info("invoking service - [ {} : {} ]", jp.getTarget().getClass().getName(), jp.getSignature().getName());
	}
	
	@Before(value="execution(* com.revature.repositories.*.*(..))")
	public void logBeforeDAO(JoinPoint jp) {
		log.debug("calling dao method - [ {} : {} ]: with args", jp.getTarget().getClass().getName(), jp.getSignature().getName());
		for (Object arg : jp.getArgs()) {
			log.debug("{} : {}", arg.getClass().getName(), arg.toString());
		}
	}
	
	@AfterReturning(value="execution(* com.revature.repositories.*.*(..))", returning = "result")
	public void logAfterDAO(JoinPoint jp, Object result) {
		log.debug("Exiting dao method - [ {} : {} ]: returning {}", jp.getTarget().getClass().getName(), jp.getSignature().getName(), result.toString());
	}
}
