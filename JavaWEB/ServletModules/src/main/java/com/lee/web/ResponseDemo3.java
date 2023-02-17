package com.lee.web;

import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ResponseDemo3")
public class ResponseDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1。字符输出流
         */
        // // 设置响应头
        // response.setHeader("content-type", "text/html;charset=utf-8");
        // // 获取字符输入流
        // PrintWriter writer = response.getWriter();
        // // 写入数据
        // writer.write("<h1>李晨泽</h1>");
        /**
         * 2。字节输出流
         */
        // 读取文件
        FileInputStream fis = new FileInputStream("/Users/lee/MySkills/Interview/LearningPaths/2681676560741_.pic.jpg");
        // 获取response字节输出流
        ServletOutputStream os = response.getOutputStream();
        // 1。完成流的copy 使用Java原生书写
        // byte[] buff = new byte[1024];
        // int length = 0;
        // while ((length = fis.read(buff)) != -1) {
        //     os.write(buff, 0, length);
        // }
        // 2。完成流的copy，使用工具类
        IOUtils.copy(fis, os);
        // 关闭文件输入流
        fis.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
