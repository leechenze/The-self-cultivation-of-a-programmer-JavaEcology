package com.lee.controller;

import com.lee.domain.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ResponseController {
    // 响应页面/跳转页面
    @RequestMapping("/toJumpPage")
    public String toJumpPage() {
        System.out.println("toJumpPage");
        return "book.jsp";
    }

    // 响应文本数据
    @RequestMapping("/toText")
    @ResponseBody
    public String toText() {
        System.out.println("toText");
        return "response text";
    }

    // 响应POJO对象
    @RequestMapping("/toPojo")
    @ResponseBody
    public Book toPojo() {
        System.out.println("toPojo");
        Book user = new Book();
        user.setAge(10);
        user.setName("lee");
        return user;
    }


    // 响应POJO集合对象
    @RequestMapping("/toPojoList")
    @ResponseBody
    public List<Book> toPojoList() {
        System.out.println("toPojoList");

        Book book = new Book();
        book.setAge(10);
        book.setName("lee");
        Book book1 = new Book();
        book1.setAge(20);
        book1.setName("trump");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        bookList.add(book1);

        System.out.println(bookList);

        return bookList;
    }

}
