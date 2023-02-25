package com.lee;

import com.lee.config.SpringConfig;
import com.lee.dao.BookDao;
import com.lee.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppForAnnotation {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = (BookDao) applicationContext.getBean("bookDao");
        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookDao);
        System.out.println(bookService);
    }
}
