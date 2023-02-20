package com.lee;

import com.lee.dao.BookDao;
import com.lee.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {
    public static void main(String[] args) {
        // 获取IoC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取Bean
        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
        BookService bookService = (BookService) ctx.getBean("bookService");
        // 执行save();
        bookDao.save();
        bookService.save();
    }
}
