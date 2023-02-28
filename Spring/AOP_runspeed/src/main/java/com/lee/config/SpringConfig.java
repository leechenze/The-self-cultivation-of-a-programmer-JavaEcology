package com.lee.config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.lee")
@PropertySource("jdbc.properties")
@Import({JdbcConfig.class, MybatisConfig.class})
@EnableAspectJAutoProxy
public class SpringConfig {

}
