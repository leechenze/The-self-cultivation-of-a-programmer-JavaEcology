package com.lee.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/ACookie")
public class ACookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = "特朗普";
        // URL编码,以存储中文
        value = URLEncoder.encode(value, "UTF-8");
        // 创建cookie对象
        Cookie cookie = new Cookie("username", value);

        // 设置存活时间（1周）
        cookie.setMaxAge(60 * 60 * 24 * 7);
        // 发送Cookie，response
        response.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
