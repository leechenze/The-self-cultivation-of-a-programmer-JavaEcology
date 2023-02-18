package com.lee.web;

import com.lee.mapper.UserMapper;
import com.lee.pojo.User;
import com.lee.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 获取sqlSessionFactory对象（抽取为一个工具类）
        // String resource = "mybatis-config.xml";
        // InputStream inputStream = Resources.getResourceAsStream(resource);
        // SqlSessionFactory sqlSessionFactory =
        //         new SqlSessionFactoryBuilder().build(inputStream);
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

        // 获取sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 调用方法
        User user = userMapper.select(username, password);
        // 释放
        sqlSession.close();



        // 获取字符输出流，设置content-type
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();


        System.out.println("login success");
        // 逻辑判断
        if (user != null) {
            writer.write("登录成功");
        }else{
            writer.write("登录失败");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
