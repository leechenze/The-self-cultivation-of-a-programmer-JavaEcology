package com.lee;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        /* druidDataSource 对象 */
        DataSource druidDataSource = (DataSource) applicationContext.getBean("druidDataSource");
        /* c3p0DataSource 对象 */
        DataSource c3p0DataSource = (DataSource) applicationContext.getBean("c3p0DataSource");
        System.out.println(c3p0DataSource);
    }
}
