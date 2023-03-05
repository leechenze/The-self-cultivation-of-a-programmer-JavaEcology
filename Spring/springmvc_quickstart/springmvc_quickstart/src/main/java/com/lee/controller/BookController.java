package com.lee.controller;

import com.lee.domain.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @RequestMapping("/save")
    @ResponseBody
    public String save(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "{'module':'book save'}";
    }

    // 普通参数：请求参数名与形参名不同
    @RequestMapping("/differentName")
    @ResponseBody
    public String differentName(@RequestParam("name") String userName) {
        System.out.println("普通参数名和形参名称不同" + userName);
        return "普通参数名和形参名称不同";
    }

    // POJO参数
    @RequestMapping("/pojo")
    @ResponseBody
    public String pojo(Book book) {
        System.out.println("POJO参数" + book);
        return "POJO参数";
    }

    // 嵌套POJO参数
    @RequestMapping("/pojoContain")
    @ResponseBody
    public String pojoContain(Book book) {
        System.out.println("嵌套POJO参数" + book);
        return "嵌套POJO参数";
    }


    // 数组类型参数
    @RequestMapping("/arrayParam")
    @ResponseBody
    public String arrayParam(String[] likes) {
        System.out.println("数组类型参数" + Arrays.toString(likes));
        return "数组类型参数";
    }


    // 集合类型参数
    @RequestMapping("/listParam")
    @ResponseBody
    public String listParam(@RequestParam List<String> likes) {
        System.out.println("集合类型参数" + likes);
        return "集合类型参数";
    }




}



