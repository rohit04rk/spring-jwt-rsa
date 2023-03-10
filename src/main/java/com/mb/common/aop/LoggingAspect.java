package com.mb.common.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

	private Logger log = LogManager.getLogger();

	private static final String CONTROLLER_EXPRESSION = "execution(* com.mb.*.controller.*.*(..))";
	private static final String EXPRESSION = CONTROLLER_EXPRESSION
			+ " || execution(* com.mb.*.service.*.*(..)) || execution(* com.mb.*.dao.*.*(..))";

	/**
	 * log name of the method before every method from specified expression package
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param joinPoint
	 */
	@Before(EXPRESSION)
	public void before(JoinPoint joinPoint) {
		log.info("before execution of {} method", joinPoint.getSignature());
	}

	/**
	 * log name of the method after every method from specified expression package
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param joinPoint
	 */
	@After(EXPRESSION)
	public void after(JoinPoint joinPoint) {
		log.info("after execution of {} method", joinPoint.getSignature());
	}

	/**
	 * log the name of exception thrown method and exception message
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(value = CONTROLLER_EXPRESSION, throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("exception has been thrown in {}.{} method", joinPoint.getSignature().getDeclaringType(),
				joinPoint.getSignature().getName());
		log.error("exception message :: {}", e.getMessage());
	}

	/**
	 * log the time taken by method to execute
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(EXPRESSION)
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();

		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		log.info("execution time of {}.{} :: {} ms", className, methodName, stopWatch.getTotalTimeMillis());

		return result;
	}
}
