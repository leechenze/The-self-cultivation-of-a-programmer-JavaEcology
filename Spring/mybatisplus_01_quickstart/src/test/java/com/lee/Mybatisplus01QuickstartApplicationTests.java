package com.lee;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.dao.UserDao;
import com.lee.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Mybatisplus01QuickstartApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void save(){
        User user = new User();
        user.setName("lee");
        user.setPassword("lee");
        user.setAge(11);
        user.setTel("400888999222");
        userDao.insert(user);
    }

    @Test
    void delete() {
        userDao.deleteById(1637377735225516033L);
    }

    @Test
    void update(){
        User user = new User();
        user.setId(1L);
        user.setName("tom333");
        user.setPassword("tom333");
        userDao.updateById(user);
    }

    @Test
    void getAll() {
        List<User> userList = userDao.selectList(null);
        System.out.println(userList);
    }

    @Test
    void getById(){
        User user = userDao.selectById(1L);
        System.out.println(user);
    }

    @Test
    void getByPage(){
        IPage page = new Page(1, 2);
        userDao.selectPage(page, null);
        System.out.println("当前页码值：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总共多少页：" + page.getPages());
        System.out.println("总共多少条：" + page.getTotal());
        System.out.println("数据：" + page.getRecords());
    }

}
