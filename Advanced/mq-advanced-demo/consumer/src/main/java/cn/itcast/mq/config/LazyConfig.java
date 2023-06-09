package cn.itcast.mq.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// @Configuration
public class LazyConfig {
    /**
     * 声明一个惰性队列
     * @return
     */
    @Bean
    public Queue lazyQueue() {
        return QueueBuilder.durable("lazy.queue")
                .lazy()
                .build();
    }

    /**
     * 声明一个普通队列
     * @return
     */
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable("normal.queue")
                .build();
    }
}
