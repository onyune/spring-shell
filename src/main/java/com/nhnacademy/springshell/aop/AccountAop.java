package com.nhnacademy.springshell.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AccountAop {

    @Pointcut("execution(* com.nhnacademy.springshell.command.*.log*(..))")
    public void userLoginRecord(){}

    @Before("userLoginRecord()")
    public void authenticationLogging(JoinPoint joinPoint)throws Throwable{
        String methodName = joinPoint.getSignature().getName();

        if("login".equals(methodName)){
            Object[] args = joinPoint.getArgs();
            String id = String.valueOf(args[0]);
            String password = String.valueOf(args[0]);

            log.info("login([{}, {}])", id, password);
        }else if ("logout".equals(methodName)){
            log.info("logout([])");
        }
    }
}
