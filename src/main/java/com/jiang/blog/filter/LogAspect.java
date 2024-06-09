package com.jiang.blog.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/*

 打印日志到控制台

 */
@Component // 注册到Spring容器，必须加入这个注解
@Aspect // 该注解标示该类为切面类，切面是由通知和切点组成的
public class LogAspect {
    private final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 切点表达式有点恶心
    @Pointcut("execution(public * com.jiang.blog.controller.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinpoint) {
//        joinpoint 里面有类的信息
        // 收到请求，记录请求内容
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("URL:" + request.getRequestURL().toString());
        log.info("HTTP_MOEDTH:" + request.getMethod());
        log.info("Ip:" + request.getRemoteAddr());
        log.info("CLASS_METHOD:" + joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName());
        log.info("ARGS:" + Arrays.toString(joinpoint.getArgs()));
    }

    // 请求后信息
    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        log.info("RESPONSE:" + new ObjectMapper().writeValueAsString(res));

    }

}
