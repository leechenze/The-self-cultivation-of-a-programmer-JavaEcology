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

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 封装用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

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
        User userInstance = userMapper.selectByUsername(username);
        if (userInstance == null) {
            // 添加用户
            userMapper.add(user);
            // 提交事务
            sqlSession.commit();
            // 释放资源
            sqlSession.close();
        }else{
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("用户名已经存在");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
