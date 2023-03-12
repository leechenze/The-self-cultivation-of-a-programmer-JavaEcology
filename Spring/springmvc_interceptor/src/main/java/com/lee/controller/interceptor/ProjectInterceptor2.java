package com.lee.controller.interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ProjectInterceptor2 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle......222");

        // request和response两个参数不解释了，handler参数指代的就是原始执行的对象
        HandlerMethod hm = (HandlerMethod) handler;
        // 获取原始执行的对象
        hm.getMethod();

        // 如果返回值为false时，将拦截掉原始操作，同时后续postHandle和afterCompletion操作都将被拦截停止执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // modelAndView 封装了一些页面间跳转的相关数据 ModelAndView 指的就是MVC中的M和V
        System.out.println("postHandle......222");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Exception 对象可以捕获原始程序执行过程中出现的异常，但是程序中有统一异常处理机制，所以对exception参数也就没有特别大的需求了
        System.out.println("afterCompletion......222");
    }
}