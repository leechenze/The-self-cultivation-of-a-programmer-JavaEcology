package com.lee.service.impl;

import com.lee.dao.BookDao;
import com.lee.service.BookService;

public class BookServiceImpl implements BookService {
    // 1。取消业务层中使用new的方式创建dao对象
    private BookDao bookDao;
    public void save(){
        System.out.println("book service save...");
        // bookDao.save();
    }

    // 2。提供对应的set方法
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
