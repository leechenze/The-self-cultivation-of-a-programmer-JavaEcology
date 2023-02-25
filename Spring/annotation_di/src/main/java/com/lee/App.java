package com.lee;

import com.lee.config.SpringConfig;
import com.lee.dao.BookDao;
import com.lee.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookService bookService = applicationContext.getBean(BookService.class);
        // bookService.save();
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println(dataSource);
    }
}
