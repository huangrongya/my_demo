package com.cbest.demo.aop;

import ch.qos.logback.core.CoreConstants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Description: 日志切面
 * @Author: royle
 * @Date: 2018/09/25
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private ThreadLocal<String> methodName = new ThreadLocal<>();
    private ThreadLocal<String> traceId = new ThreadLocal<>();

    @Pointcut("execution(public * com.bifrost.demo.controller.*.*(..))")
    public void controllerLog(){ }

    @Before("controllerLog()")
    public void requestLog(JoinPoint joinPoint) {
        traceId.set(UUID.randomUUID().toString().replace("-", ""));
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String httpMethod = request.getMethod();
        String ip = request.getRemoteAddr();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String classMethod = className.substring(className.lastIndexOf(".")+1)+"."+joinPoint.getSignature().getName();
        Object[] params = joinPoint.getArgs();
        JSONObject logJson = new JSONObject();
        long timeStamp = System.currentTimeMillis();
        logJson.put("Url", url);
        logJson.put("Timestamp", timeStamp);
        logJson.put("T", new Date(timeStamp).toString());
        logJson.put("L", "INFO");
        logJson.put("Method", httpMethod);
        logJson.put("Class", classMethod);
        logJson.put("IP", ip);
        logJson.put("TraceID", traceId.get());
        logJson.put("params", JSONObject.toJSONString(params));
        log.info(logJson.toJSONString().concat(CoreConstants.LINE_SEPARATOR));
        methodName.set(classMethod);
    }

    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void responseLog(Object ret) {
        JSONObject logJson = new JSONObject();
        long timeStamp = System.currentTimeMillis();
        logJson.put("Timestamp", timeStamp);
        logJson.put("T", new Date(timeStamp).toString());
        logJson.put("L", "INFO");
        logJson.put("Class", methodName.get());
        logJson.put("traceId", traceId.get());
        logJson.put("response", ret);
        log.info(logJson.toJSONString().concat(CoreConstants.LINE_SEPARATOR));
    }


    @AfterThrowing(pointcut="execution(* com.bifrost.demo.service..*.*(..))",throwing="e")
    public void afterThrowing(JoinPoint jp,RuntimeException e){
        long timeStamp = System.currentTimeMillis();
        JSONObject logJson = new JSONObject();
        logJson.put("Timestamp", timeStamp);
        logJson.put("T", new Date(timeStamp).toString());
        logJson.put("L", "ERROR");
        logJson.put("CLASS", jp.getSignature().getDeclaringType());
        logJson.put("traceId", traceId.get());
        logJson.put("args", Arrays.toString(jp.getArgs()));
        logJson.put("e", e.getLocalizedMessage());
        log.error(logJson.toJSONString().concat(CoreConstants.LINE_SEPARATOR));
    }

}
