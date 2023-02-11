package com.lee1.pojo;

import com.lee1.mapper.UserMapper1;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class UserMapper1Test {

    @Test
    public void testTestSelect() throws IOException {

        // 1.获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 2.获取SqlSession对象（默认是false，即开启事务，传入true，以设置自动提交事务, 关闭事务）
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 3.获取Mapper接口代理对象
        UserMapper1 userMapper1 = sqlSession.getMapper(UserMapper1.class);
        // 4.执行方法
        String username = "trump";
        String password = "123";

        // 多个参数
        // User user = userMapper1.select(username, password);
        // 单个参数
        User user = userMapper1.select(new HashSet());


        System.out.println(user);
        // 5.释放资源
        sqlSession.close();
    }
}
