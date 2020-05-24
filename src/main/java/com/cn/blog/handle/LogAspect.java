package com.cn.blog.handle;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.cn.blog.controller.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("-----------doBefore");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestLog requestLog = new RequestLog();
        requestLog.url=attributes.getRequest().getRequestURI();
        requestLog.ip = attributes.getRequest().getRemoteAddr();
        Signature signature = joinPoint.getSignature();
        requestLog.method =signature.getDeclaringTypeName()+"."+signature.getName();
        requestLog.args = joinPoint.getArgs();
        logger.info("request:{}",requestLog);
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void  doAfterReturn(Object result){
        logger.info("Result:{}",result);
    }
    private class RequestLog{
        private String url;
        private String ip;
        private String method;
        private Object[] args;

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", method='" + method + '\'' +
                    ", args='" + args + '\'' +
                    '}';
        }
    }
}
