package com.lee.service.impl;

import com.lee.dao.BookDao;
import com.lee.dao.impl.BookDaoImpl;
import com.lee.service.BookService;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    public void save(){
        System.out.println("book service save...");
        // bookDao.save();
    }
}
