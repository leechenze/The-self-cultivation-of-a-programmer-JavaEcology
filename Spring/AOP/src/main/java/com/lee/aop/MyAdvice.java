package com.lee.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {
    /**
     * AOP入门案例
     */
    // 切入点
    // @Pointcut("execution(void com.lee.dao.BookDao.update())")
    @Pointcut("execution(int com.lee.dao.BookDao.select())")
    private void pt() {}


    // 共性功能方法
    // @Before("pt()")
    // public void method() {
    //     System.out.println(System.currentTimeMillis());
    // }


    /**
     * AOP通知类型
     */
    // @Before("pt()")
    public void before() {
        System.out.println("before advice ...");
    }
    // @After("pt()")
    public void after() {
        System.out.println("after advice ...");
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before advice ...");
        // 对原始方法的调用（需要抛出异常，因为原始方法的调用无法预期是否有异常，所以在这里先强制抛出异常）
        Object res = pjp.proceed();
        System.out.println("around after advice ...");
        return res;
    }

    // @AfterReturning("pt()")
    public void afterReturning() {
        System.out.println("afterReturning advice ...");
    }

    // @AfterThrowing("pt()")
    public void afterThrowing() {
        System.out.println("afterThrowing advice ...");
    }
}
