package cn.lee.order;

import cn.lee.order.config.DefaultFeignConfiguration;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("cn.lee.order.mapper")
@SpringBootApplication
@EnableDiscoveryClient
// 开启Feign的功能
@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class)
public class OrderApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 修改负载均衡策略（是轮询或是随机的规则策略，注意需要导入ribbon的相关依赖）
    // @Bean
    // public IRule randomRule() {
    //     return new RoundRobinRule();
    // }

}