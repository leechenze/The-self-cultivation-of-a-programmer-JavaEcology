package com.lee.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@WebServlet("/RequestDemo3")
public class RequestDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // post请求方式解决乱码：设置字符输入流编码为UTF-8，因为post底层是走的getReader() 获取字符输入流
        request.setCharacterEncoding("UTF-8");
        // get请求方式解决乱码：tomcat进行url解码默认字符集是 iso-8859-1的方式
        // 1。先对乱码数据进行编码：转为字节数组
        String username = request.getParameter("username");
        System.out.println("解决乱码前的username" + username);
        byte[] bytes = username.getBytes(StandardCharsets.ISO_8859_1);
        // 2。对字节数组解码
        username = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("解决乱码后的username" + username);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
