package com.lee;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.dao.UserDao;
import com.lee.domain.User;
import com.lee.domain.query.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Mybatisplus02DqlApplicationTests1 {

    @Autowired
    private UserDao userDao;

    @Test
    void getAll() {
        // 模拟前端传递来的查询参数的
        UserQuery userQuery = new UserQuery();
        userQuery.setAge(1);
        userQuery.setAge2(10);

        // null判定（如果写成if，else判断很不友好，此时可以使用queryWrapper提供的方法，其中的参数）
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        lambdaQueryWrapper
                .lt(null != userQuery.getAge2(), User::getAge, userQuery.getAge2())
                .gt(null != userQuery.getAge(), User::getAge, userQuery.getAge());
        List<User> userList = userDao.selectList(lambdaQueryWrapper);
        System.out.println(userList);

    }

}
