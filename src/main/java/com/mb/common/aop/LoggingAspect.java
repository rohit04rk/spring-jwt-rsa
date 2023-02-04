package com.mb.common.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LogManager.getLogger();

	private static final String EXEC_EXP = "execution(* com.mb.*.controller.*.*(..)) "
			+ "|| execution(* com.mb.*.service.*.*(..)) || execution(* com.mb.*.dao.*.*(..))";

	@Before(EXEC_EXP)
	public void before(JoinPoint joinPoint) {
		log.info("before execution of {} method", joinPoint.getSignature());
	}

	@After(EXEC_EXP)
	public void after(JoinPoint joinPoint) {
		log.info("after execution of {} method", joinPoint.getSignature());
	}

	@AfterReturning(value = EXEC_EXP, returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		log.info("returned with value {}", joinPoint);
	}

	@Around(EXEC_EXP)
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
