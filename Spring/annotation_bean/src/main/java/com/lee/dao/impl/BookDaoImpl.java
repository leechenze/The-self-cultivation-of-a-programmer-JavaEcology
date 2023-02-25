package com.lee.dao.impl;

import com.lee.dao.BookDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


// @Component("bookDao")
@Repository("bookDao")
public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save");
    }
}
