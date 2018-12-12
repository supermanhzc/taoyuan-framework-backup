package com.taoyuan.framework.bs.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.taoyuan.framework.bs.entity.operlog.TyOperLog;
import com.taoyuan.framework.bs.service.operlog.TyOperLogService;
import com.taoyuan.framework.common.entity.TyUserRolePermission;
import com.taoyuan.framework.common.http.TyResponse;
import com.taoyuan.framework.common.http.TySession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private TyOperLogService tyOperLogService;

    @Pointcut("@annotation(com.taoyuan.framework.bs.aspect.OperControllerLog)")
    public void controllerAspect(){};

    @Around("controllerAspect()")
    public Object recordOperLog(ProceedingJoinPoint joinPoint) throws Throwable{
        TyOperLog tyOperLog = new TyOperLog();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("Receive a request from host: {}, url: {} {}, querystr: {}, args: {}", request.getRemoteAddr(),
                request.getMethod(), request.getRequestURL().toString(), JSON.toJSONString(request.getParameterMap(),
                        SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue),
                JSON.toJSONString(joinPoint.getArgs()));
        tyOperLog.setIp(request.getRemoteAddr());
        TyUserRolePermission currentUser = TySession.getCurrentUser();
        if(null != currentUser){
            tyOperLog.setUserId(currentUser.getUserId());
        }
        tyOperLog.setRequest(this.getRequestInfo(joinPoint, request));
        this.setControllerAnnotationArgs(joinPoint, tyOperLog);
        tyOperLog.setStartTime(new Date());
        Object proceed = joinPoint.proceed();
        tyOperLog.setEndTime(new Date());
        TyResponse response = (TyResponse) proceed;
        log.info("Response is " + response);
        tyOperLog.setRespCode(response.getCode());
        tyOperLog.setRespMessage(response.getMsg());
        tyOperLog.setRespData(JSONObject.toJSONString(response.getData()));
        try {
            tyOperLogService.save(tyOperLog);
        }catch (Exception e){
            log.error("oper log record error: {}", e.getMessage());
        }
        return proceed;
    }

    @AfterThrowing(pointcut = "controllerAspect()",throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable{
        TyOperLog tyOperLog = new TyOperLog();
        Object proceed = null ;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("Receive a request from host: {}, url: {} {}, querystr: {}, args: {}", request.getRemoteAddr(),
                request.getMethod(), request.getRequestURL().toString(), JSON.toJSONString(request.getParameterMap(),
                        SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue),
                JSON.toJSONString(joinPoint.getArgs()));

        tyOperLog.setIp(request.getRemoteAddr());
        TyUserRolePermission currentUser = TySession.getCurrentUser();
        if(null != currentUser){
            tyOperLog.setUserId(currentUser.getUserId());
        }
        tyOperLog.setEndTime(new Date());
        tyOperLog.setRequest(this.getRequestInfo(joinPoint, request));
        tyOperLog.setRespCode(e.getClass().getName());
        tyOperLog.setRespMessage(e.getMessage());
        log.info("exception happened. " + e.getMessage());
        tyOperLogService.save(tyOperLog);
    }

    private String getRequestInfo(JoinPoint joinPoint, HttpServletRequest request){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", request.getRequestURL());
        jsonObject.put("querystr", JSON.toJSONString(request.getParameterMap(),
                SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue));
        JSONObject headJson = new JSONObject();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String key = headerNames.nextElement();
            headJson.put(key, request.getHeader(key));
        }
        jsonObject.put("head", headJson);
        jsonObject.put("body", joinPoint.getArgs());
        return jsonObject.toJSONString();
    }

    private void setControllerAnnotationArgs(JoinPoint point, TyOperLog tyOperLog) throws  Exception{
        String targetName = point.getTarget().getClass().getName() ;
        String methodName = point.getSignature().getName() ;
        Object[] args = point.getArgs() ;
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods() ;
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length){
                    OperControllerLog operControllerLog = method.getAnnotation(OperControllerLog.class);
                    tyOperLog.setModule(operControllerLog.module());
                    tyOperLog.setType(operControllerLog.type());
                    break;
                }
            }
        }
    }

}
