package com.lee;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.dao.UserDao;
import com.lee.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class Mybatisplus02DqlApplicationTests2 {
    @Autowired
    private UserDao userDao;

    @Test
    void getAll() {
        /**
         * 正常格式
         */
        // 查询投影：
        // QueryWrapper queryWrapper = new QueryWrapper();
        // queryWrapper.select("name", "gender","password");
        // List<User> userList = userDao.selectList(queryWrapper);
        // System.out.println(userList);
        // 查询分组：（count（*））用来做字段统计，是一个User模型之外的字段，所以定义为了一个Map来接收结果
        // QueryWrapper queryWrapper = new QueryWrapper();
        // queryWrapper.select("count(*) as count", "gender").groupBy("gender");
        // List<Map<String, Object>> resultList = userDao.selectMaps(queryWrapper);
        // System.out.println(resultList);
        /**
         * Lambda格式
         */
        // 查询投影：
        // LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        // lambdaQueryWrapper.select(User::getName, User::getPassword, User::getGender);
        // List<User> userList = userDao.selectList(lambdaQueryWrapper);
        // System.out.println(userList);
    }

}
