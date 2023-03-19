package com.lee;

import com.lee.dao.BookDao;
import com.lee.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot04MybatisApplicationTests {

    @Autowired
    private BookDao bookDao;

    @Test
    public void getById() {
        Book book = bookDao.getById(3);
        System.out.println(book);
    }

}
