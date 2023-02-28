package com.lee.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(* com.lee.dao.BookDao.findName(..))")
    private void pc() {}

    @Before("pc()")
    public void before(JoinPoint jp) {
        Object[] args = jp.getArgs();
        System.out.println(Arrays.toString(args));
        System.out.println("before advice ...");
    }

    // @After("pc()")
    public void after() {
        System.out.println("after advice ...");
    }

    // @Around("pc()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object res = pjp.proceed();
        return res;
    }

    // @AfterReturning("pc()")
    public void afterReturning() {
        System.out.println("afterReturning advice ...");
    }

    // @AfterThrowing("pc()")
    public void afterThrowing() {
        System.out.println("afterThrowing advice ...");
    }


}
