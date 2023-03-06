package com.lee.controller;


import com.lee.domain.Book;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/jackson")
public class JackSonController {
    // 集合参数的JSON格式
    @RequestMapping("/jsonlist")
    @ResponseBody
    public String jsonlist(@RequestBody List<String> likes) {
        System.out.println("集合参数的JSON格式" + likes);
        return "集合参数的JSON格式";
    }

    // pojo参数的JSON格式
    @RequestMapping("/jsonpojo")
    @ResponseBody
    public String jsonpojo(@RequestBody Book book) {
        System.out.println("pojo参数的JSON格式" + book);
        return "pojo参数的JSON格式";
    }

    // 集合包含多个pojo的JSON格式
    @RequestMapping("/jsonlistpojo")
    @ResponseBody
    public String jsonlistpojo(@RequestBody List<Book> list) {
        System.out.println("集合包含多个pojo的JSON格式" + list);
        return "集合包含多个pojo的JSON格式";
    }

    // 日期参数
    @RequestMapping("/dateParam")
    @ResponseBody
    public String dateParam(@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date date) {
        System.out.println("日期参数" + date);
        return "日期参数";
    }




}
