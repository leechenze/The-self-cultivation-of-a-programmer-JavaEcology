package com.lee;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.dao.UserDao;
import com.lee.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Mybatisplus02DqlApplicationTests3 {
    @Autowired
    private UserDao userDao;

    @Test
    public void getAll() {
        /**
         * eq（=）
         */
        // LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        // lambdaQueryWrapper.eq(User::getName, "hilarie").eq(User::getPassword, "hilarie");
        // User user = userDao.selectOne(lambdaQueryWrapper);
        // System.out.println(user);

        /**
         * lt (<=)
         * le (<)
         * gt (>=)
         * ge (>)
         * between (。。。之间。。。)
         */
        // LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        // lambdaQueryWrapper.between(User::getAge, "10", "20");
        // List<User> userList = userDao.selectList(lambdaQueryWrapper);
        // System.out.println(userList);

        /**
         * like (模糊匹配)
         */
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        lambdaQueryWrapper.like(User::getName, "m");
        List<User> userList = userDao.selectList(lambdaQueryWrapper);
        System.out.println(userList);


    }
}
