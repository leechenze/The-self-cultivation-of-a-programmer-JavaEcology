package com.lee.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.lee.controller")
@EnableWebMvc // 开启JSON数据转换对象的功能
public class SpringMvcConfig {

}
