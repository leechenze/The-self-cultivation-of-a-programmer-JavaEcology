package com.lee1;

import com.lee1.mapper.UserMapper;
import com.lee1.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisDemo01 {
    public static void main(String[] args) throws IOException {
        /**
        * 1.加载mybatis的核心配置文件，获取 sqlSessionFactory对象来执行sql语句
        */
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        /**
         * 2.获取SqlSession对象，用它执行sql语句
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();

        /**
         * 3.执行sql
         * @params1 sql映射文件某个映射的名称空间 . 某条sql语句的唯一标识Id
         * @params2
         */
        // List<User> users = sqlSession.selectList("user.selectAll");

        // 3.1获取UserMapper接口的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.selectAll();
        System.out.println(users);



        /**
         * 4.释放资源
         */
        sqlSession.close();
    }
}
