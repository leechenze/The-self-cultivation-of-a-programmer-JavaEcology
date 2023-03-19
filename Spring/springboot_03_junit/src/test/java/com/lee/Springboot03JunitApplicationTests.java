package com.lee;

import com.lee.controller.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest(classes = Springboot03JunitApplication.class)
@SpringBootTest
class Springboot03JunitApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    public void save() {
        bookService.save();
    }

}
