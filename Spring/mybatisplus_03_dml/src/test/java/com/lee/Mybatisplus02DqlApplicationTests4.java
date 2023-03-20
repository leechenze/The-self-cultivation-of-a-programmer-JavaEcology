package com.lee;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.dao.UserDao;
import com.lee.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Mybatisplus02DqlApplicationTests4 {
    @Autowired
    private UserDao userDao;

    @Test
    public void getAll() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<User> userList = userDao.selectList(lambdaQueryWrapper);
        System.out.println(userList);
    }
}
