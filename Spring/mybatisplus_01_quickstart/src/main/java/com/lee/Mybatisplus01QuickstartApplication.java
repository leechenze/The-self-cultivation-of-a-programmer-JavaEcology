package com.lee;

import com.lee.config.MyBatisPlusConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
// 这个方法比较土，直接MyBatisPlusConfig类上声明为@Configuration也可以
// @Import({MyBatisPlusConfig.class})
public class Mybatisplus01QuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(Mybatisplus01QuickstartApplication.class, args);
    }

}
