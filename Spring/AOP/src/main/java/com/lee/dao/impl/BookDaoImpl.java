package com.lee.dao.impl;

import com.lee.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {

    public void save() {
        System.out.println(System.currentTimeMillis());
        System.out.println("book dao save ...");
    }

    public void update() {
        System.out.println("book dao update ...");
    }

    public int select() {
        System.out.println("book dao select ...");
        // 异常代码段
        // int i = 1/0;
        return 100;
    }
}
