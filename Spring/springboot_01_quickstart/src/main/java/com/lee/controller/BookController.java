package com.lee.controller;


import com.lee.domain.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")

public class BookController {
    // 读取方式一
    @Value("${lesson}")
    private String lesson;
    @Value("${server.port}")
    private String port;
    @Value("${enterprise.subject[0]}")
    private String subject0;

    // 读取方式二
    @Autowired
    private Environment environment;

    // 读取方式三
    @Autowired
    private Enterprise enterprise;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id) {
        System.out.println("id ==> " + id);
        System.out.println("===============================");
        System.out.println("lesson ==> " + lesson);
        System.out.println("port ==> " + port);
        System.out.println("subject0 ==> " + subject0);
        System.out.println("===============================");
        System.out.println("lesson ==> " + environment.getProperty("lesson"));
        System.out.println("port ==> " + environment.getProperty("server.port"));
        System.out.println("subject0 ==> " + environment.getProperty("enterprise.subject[0]"));
        System.out.println("===============================");
        System.out.println("enterprise.toString() ==> " + enterprise.toString());
        return "hello spring boot";
    }
}
