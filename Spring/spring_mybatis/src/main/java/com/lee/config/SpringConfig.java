package com.lee.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.lee")
@PropertySource({"jdbc.properties"})
@Import({JDBCConfig.class, MyBatisConfig.class})
public class SpringConfig {
    
}
