package com.lee.config;

import org.springframework.context.annotation.*;


@Configuration
@ComponentScan("com.lee")
@PropertySource({"jdbc.properties"})
@Import({JDBCConfig.class})
public class SpringConfig {

}
