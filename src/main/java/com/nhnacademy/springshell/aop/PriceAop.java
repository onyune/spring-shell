package com.nhnacademy.springshell.aop;

import com.nhnacademy.springshell.domain.Account;
import com.nhnacademy.springshell.service.AuthenticationService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class PriceAop {
    private final AuthenticationService authenticationService;

    @Pointcut("execution(* com.nhnacademy.springshell.command.*.*(..))")
    public void allCommands(){}

    @Pointcut("execution(* com.nhnacademy.springshell.command.*.log*(..))")
    public void userLoginRecord(){}

    @Pointcut("allCommands() && !userLoginRecord()")
    public void allRecordNotLoginLogout(){}

    @Around("allRecordNotLoginLogout()")
    public Object etcLog(ProceedingJoinPoint joinPoint) throws Throwable{
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String classPath = className + "." + methodName;

        Account account = authenticationService.getCurrentUser();
        if(account == null){
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        log.info("----- {} class {}({}) ---->",account.getName(),classPath, Arrays.toString(args));
        Object o  = joinPoint.proceed();
        log.info("<------ {} class {}({}) ------",account.getName(),classPath, o);
        return o;
    }
}
