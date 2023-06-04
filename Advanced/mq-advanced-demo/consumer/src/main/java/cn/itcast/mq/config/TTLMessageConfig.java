package cn.itcast.mq.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TTLMessageConfig {

    /**
     * 声明延迟交换机
     * @return
     */
    @Bean
    public DirectExchange ttlDirectExchange() {
        return new DirectExchange("ttl.direct");
    }

    /**
     * 声明延迟队列，并绑定延迟时间和死信交换机和死信RoutingKey。
     * @return
     */
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder
                .durable("ttl.queue")
                .ttl(10000) // 设置队列的延迟时间。
                .deadLetterExchange("dl.direct")
                .deadLetterRoutingKey("dl.key")
                .build();
    }

    /**
     * 绑定ttl交换机（延迟交换机）和ttl队列（延迟队列）
     */
    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with("ttl.key");
    }

}
