package com.lee.dao.impl;

import com.lee.dao.BookDao;
import org.springframework.stereotype.Repository;


@Repository("bookDao1")
public class BookDaoImpl1 implements BookDao {
    public void save() {
        System.out.println("book dao1 save");
    }
}
