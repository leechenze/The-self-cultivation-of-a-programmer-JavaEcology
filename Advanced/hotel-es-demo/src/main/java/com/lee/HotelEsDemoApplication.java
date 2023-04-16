package com.lee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.lee.hotel.mapper")
@SpringBootApplication
public class HotelEsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelEsDemoApplication.class, args);
    }

}


