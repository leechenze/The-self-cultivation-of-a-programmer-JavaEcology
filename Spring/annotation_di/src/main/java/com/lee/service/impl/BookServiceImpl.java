package com.lee.service.impl;

import com.lee.dao.BookDao;
import com.lee.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    // @Qualifier("bookDao1")
    private BookDao bookDao;

    public void save() {
        System.out.println("book service save");
        bookDao.save();
    }
}
