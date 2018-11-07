package com.taoyuan.framework.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

	@Pointcut("execution(public * com.taoyuan..controller.*Controller.*(..))")
	public void intfLog() {
	}

	@Before("intfLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		log.info("Receive a request from host: {}, url: {} {}, querystr: {}, args: {}", request.getRemoteAddr(),
				request.getMethod(), request.getRequestURL().toString(), JSON.toJSONString(request.getParameterMap(),
						SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue),
				JSON.toJSONString(joinPoint.getArgs()));
	}

	@AfterReturning(returning = "ret", pointcut = "intfLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		log.info("Response is " + ret);
	}

}
