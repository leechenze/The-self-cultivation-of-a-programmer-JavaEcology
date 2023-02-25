package com.lee.dao.impl;

import com.lee.dao.BookDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


@Repository("bookDao")
public class BookDaoImpl implements BookDao {

    // @Value("leechenze")
    @Value("${name}")
    private String name;

    public void save() {
        System.out.println("book dao save" + " " + name);
    }
}
