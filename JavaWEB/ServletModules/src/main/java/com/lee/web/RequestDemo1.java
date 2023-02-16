package com.lee.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/req")
public class RequestDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 请求行
        String method = req.getMethod();
        String contextPath = req.getContextPath();
        StringBuffer url = req.getRequestURL();
        String uri = req.getRequestURI();
        String query = req.getQueryString();

        System.out.println(method);
        System.out.println(contextPath);
        System.out.println(url);
        System.out.println(uri);
        System.out.println(query);

        // 请求头
        String agent = req.getHeader("user-agent");
        System.out.println(agent);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 请求体：post的请求参数
        // 1。获取字符输入流
        BufferedReader br = req.getReader();
        // 2。读取数据
        String line = br.readLine();
        System.out.println(line);

    }
}
