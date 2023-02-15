package com.lee.web;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(urlPatterns = "/demo2",loadOnStartup = 1)
public class ServletDemo2 implements Servlet {
    private ServletConfig servletConfig;
    /**
     * 初始化方法
     *  调用时机：默认情况下Servlet第一次备访问时调用，会被调用一次
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
        System.out.println("Init 方法执行");
    }

    /**
     * 提供服务的方法：每一次Servlet被访问时调用，会调用多次
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("service 方法执行");
    }

    /**
     * 销毁方法：在内存释放时或服务器关闭时，Servlet对象会被销毁，只会调用一次
     */
    @Override
    public void destroy() {
        System.out.println("destory 方法执行");
    }
    @Override
    public String getServletInfo() {
        return "null";
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }
}
