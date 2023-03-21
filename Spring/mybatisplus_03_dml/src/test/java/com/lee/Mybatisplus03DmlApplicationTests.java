package com.lee;

import com.lee.dao.UserDao;
import com.lee.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Mybatisplus03DmlApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void save(){
        User user = new User();
        // user.setId(666L);
        user.setName("heima");
        user.setPassword("heima");
        user.setAge(11);
        user.setTel("102220000");
        userDao.insert(user);
    }


    @Test
    void delete() {
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(1638172143701307393L);
        userIdList.add(1638174811878334466L);
        userIdList.add(666L);
        userDao.deleteBatchIds(userIdList);
    }

    /** 逻辑删除 */
    @Test
    void logicDelete() {
        userDao.deleteById(2L);
        System.out.println(userDao.selectList(null));
    }

    /** 乐观锁 */
    @Test
    void update() {
        // 方式一: 常规方式
        // User user = new User();
        // user.setId(3L);
        // user.setName("trump999");
        // // 首先，触发version相关的数据，user对象必须要有version这个属性（字段）
        // user.setVersion(1);
        // userDao.updateById(user);


        // 方式二：通过修改的数据id将数据查出来，这样他本来就有version字段了
        // User user1 = userDao.selectById(3L);
        // user1.setName("trump100");
        // userDao.updateById(user1);

        // 模拟A，B用户操作，对乐观锁进行验证
        User userA = userDao.selectById(3L);    // version 3
        User userB = userDao.selectById(3L);    // version 3

        // 会成功
        userA.setName("trump aaa");
        userDao.updateById(userA);    // version 4

        // 不会成功
        userB.setName("trump bbb");
        // 此时 userB 还是3，但是userA执行后 version 已经变成4了，所以条件已经不成立了
        // 因为已经配置了乐观锁拦截器的作用，就不会执行了
        userDao.updateById(userB);    // version 3

    }

}
