package com.lee.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class MyAdvice {
    /**
     * 匹配业务层的所有方法
     */
    @Pointcut("execution(* com.lee.service.*Service.*(..))")
    private void servicePt() {}

    @Around("servicePt()")
    public void runspeed(ProceedingJoinPoint pjp) throws Throwable {

        Signature signature = pjp.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();


        long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++) {
            pjp.proceed();
        }
        long end = System.currentTimeMillis();
        System.out.println("业务层接口执行万次效率: " + className + "." + methodName + " ==> " + (end - start) + "ms");
    }
}
