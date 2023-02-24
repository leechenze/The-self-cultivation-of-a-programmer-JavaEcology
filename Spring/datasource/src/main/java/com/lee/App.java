package com.lee;

import com.lee.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        /** 第三方资源管理,第三方Bean */
        /* druidDataSource 对象 */
        // DataSource druidDataSource = (DataSource) applicationContext.getBean("druidDataSource");
        /* c3p0DataSource 对象 */
        // DataSource c3p0DataSource = (DataSource) applicationContext.getBean("c3p0DataSource");
        // System.out.println(c3p0DataSource);


        /** 加载properties文件 */
        /**
         * 使用userDao来验证 applicationContext中配置的文件是否读取正确
         */
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        userDao.save();

    }
}
