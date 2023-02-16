package com.lee.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/ResponseDemo1")
public class ResponseDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ResponseDemo1");
        // 重定向
        // 1。设定响应状态码302
        // response.setStatus(302);
        // 2。设置响应头 Location
        // response.setHeader("Location", "/ServletModules/ResponseDemo2");
        // 简化方式完成重定向（等同于 第一步 + 第二步）
        // response.sendRedirect("/ServletModules/ResponseDemo2");
        // response.sendRedirect("https://www.baidu.com");
        // 动态虚拟路径
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/ResponseDemo2");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
