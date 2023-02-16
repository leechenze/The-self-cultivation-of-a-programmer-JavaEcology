package com.lee.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/req2")
public class RequestDemo2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1。获取所有参数的map集合
        Map<String, String[]> map = req.getParameterMap();
        for (String key : map.keySet()) {
            // 获取Key
            System.out.print(key + ":");
            // 获取值
            String[] values = map.get(key);
            for (String val : values) {
                System.out.print(val + ",");
            }
            System.out.println();
        }
        System.out.println("=================================");
        // 2。根据key参数获取参数值，数组的参数值
        String[] hobbies = req.getParameterValues("hobby");
        for (String key : hobbies) {
            System.out.println(key);
        }
        System.out.println("=================================");
        // 3。根据Key参数获取参数子：单个参数值
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username);
        System.out.println(password);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
