package com.lee.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

// 定义servlet容器启动的配置类，继承的类是固定的，来进行加载spring的配置
public class ServletContainerInitConfig extends AbstractDispatcherServletInitializer {
    // 加载SpringMVC容器配置
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringMvcConfig.class);
        return ctx;
    }

    // 设置哪些请求归属SpringMVC处理
    @Override
    protected String[] getServletMappings() {
        // String[]{"/"} 表示所有请求归SpringMVC处理
        return new String[]{"/"};
    }

    // 加载Spring容器配置，因为现在只有SpringMVC容器，没有Spring容器，所以直接Return null即可；
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
