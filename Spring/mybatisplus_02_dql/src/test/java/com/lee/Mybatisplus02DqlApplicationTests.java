package com.lee;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.dao.UserDao;
import com.lee.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Mybatisplus02DqlApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void getAll() {
        // 方式一：按条件查询对应的操作
        // QueryWrapper queryWrapper = new QueryWrapper();
        // queryWrapper.lt("age", 9);
        // List<User> userList = userDao.selectList(queryWrapper);
        // System.out.println(userList);

        // 方式二：lambda表达式按条件查询
        // QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        // queryWrapper.lambda().lt(User::getAge, 9);
        // List<User> userList = userDao.selectList(queryWrapper);
        // System.out.println(userList);

        // 方式三：lambda表达式按条件查询
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        // 支持链式调用（年龄小于10 或者 年龄大于6的，也就是全部数据）
        // 如果没有.or()的调用，那么就是并且的关系
        lambdaQueryWrapper.lt(User::getAge, 10).or().gt(User::getAge, 6);
        List<User> userList = userDao.selectList(lambdaQueryWrapper);
        System.out.println(userList);

    }

}
