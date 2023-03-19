package com.lee.controller.impl;

import com.lee.controller.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public void save() {
        System.out.println("book service is running");
    }
}
