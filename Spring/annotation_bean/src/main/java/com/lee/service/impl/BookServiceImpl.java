package com.lee.service.impl;

import com.lee.dao.BookDao;
import lee.com.service.BookService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// @Component
@Service
public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void save() {
        System.out.println("book service save");
        bookDao.save();
    }
}
