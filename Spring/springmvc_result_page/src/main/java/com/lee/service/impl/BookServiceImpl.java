package com.lee.service.impl;

import com.lee.controller.Code;
import com.lee.dao.BookDao;
import com.lee.domain.Book;
import com.lee.exception.BusinessException;
import com.lee.exception.SystemException;
import com.lee.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public boolean save(Book book) {
        return bookDao.save(book) > 0;
    }

    @Override
    public boolean update(Book book) {
        return bookDao.update(book) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return bookDao.delete(id) > 0;
    }

    @Override
    public Book getById(Integer id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
