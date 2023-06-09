package cn.itcast.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 在消费者中编码，因为消费者（consumer）在启动时可以创建队列和交换机。
 */
// @Configuration
public class CommonConfig {
    @Bean
    public DirectExchange simpleDirect() {
        // 参数为：交换机名称，是否持久化，当交换机没有queue绑定时是否自动删除该交换机
        return new DirectExchange("simple.direct", true, false);
    }

    @Bean
    public Queue simpleQueue() {
        return QueueBuilder.durable("simple.queue").build();
    }

}
