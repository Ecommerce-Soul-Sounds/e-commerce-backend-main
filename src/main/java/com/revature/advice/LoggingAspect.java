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

@Aspect
@Component
public class LoggingAspect {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private ObjectMapper om = new ObjectMapper();
	
	@Before(value="execution(* com.revature.controllers.*.*(..))")
	public void LogBeforeController(JoinPoint jp) throws JsonProcessingException {
		log.info(String.format("Incoming request routed to - [ %s : %s ]", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
		
		String requestJSON = om.writeValueAsString(jp.getArgs()[0]);
	
		log.info("Request body is: " + requestJSON);
	}
	
	@AfterReturning(value="execution(* com.revature.controllers.*.*(..))", returning = "result")
	public void LogAfterController(JoinPoint jp, Object result) {
		log.info("sending response: " + result);
	}
	
	@Before(value="execution(* com.revature.services.*.*(..))")
	public void LogBeforeService(JoinPoint jp) {
		log.info(String.format("invoking service - [ %s : %s ]", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
	}
	
	@Before(value="execution(* com.revature.repositories.*.*(..))")
	public void LogBeforeDAO(JoinPoint jp) throws JsonProcessingException {
		log.debug(String.format("calling dao method - [ %s : %s ]: with args", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
		for (Object arg : jp.getArgs()) {
			log.debug(arg.getClass().getName() + ": " + om.writeValueAsString(arg));
		}
	}
	
	@AfterReturning(value="execution(* com.revature.repositories.*.*(..))", returning = "result")
	public void LogAfterDAO(JoinPoint jp, Object result) throws JsonProcessingException {
		log.debug(String.format("Exiting dao method - [ %s : %s ]: returning %s", jp.getTarget().getClass().getName(), jp.getSignature().getName(), om.writeValueAsString(result)));
	}
}
