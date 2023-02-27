package com.lee.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {
    // 切入点
    @Pointcut("execution(void com.lee.dao.BookDao.update())")
    private void pt() {}


    // 共性功能方法
    @Before("pt()")
    public void method() {
        System.out.println(System.currentTimeMillis());
    }
}
