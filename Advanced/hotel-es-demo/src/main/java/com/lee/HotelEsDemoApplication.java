package com.lee;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@MapperScan("com.lee.hotel.mapper")
@SpringBootApplication
public class HotelEsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelEsDemoApplication.class, args);
    }

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://127.0.0.1:9200")
            )
        );
    }

}


