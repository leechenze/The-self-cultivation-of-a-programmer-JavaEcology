package com.lee.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/demo6")
public class ServletDemo6 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 使用request获取请求数据；url?name=trump
        String name = request.getParameter("name");

        // 使用response对象设置响应的数据
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.getWriter().write("<h1>" + name + ",欢迎你！</h1>");
    }
}
