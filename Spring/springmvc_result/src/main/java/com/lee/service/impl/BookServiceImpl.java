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
        bookDao.save(book);
        return true;
    }

    @Override
    public boolean update(Book book) {
        bookDao.update(book);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        bookDao.delete(id);
        return true;
    }

    @Override
    public Book getById(Integer id) {
        // 业务异常模拟
        if (id < 0) {
            throw new BusinessException(Code.BUSINESS_ERR, "id异常，请重试");
        }
        // 系统异常模拟
        // 将可能出现的异常进行包装，转换成自定义异常（try catch）
        try {
            int i = 1 / 0;
        } catch (Exception exception) {
            throw new SystemException(Code.SYSTEM_ERR, "服务器异常，请重试");
        }
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
