package com.lee.hotel.config;

import com.lee.hotel.constants.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    /**
     * 定义交换机
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MqConstants.HOTEL_EXCHANGE, true, false);
    }

    /**
     * 定义插入的队列
     * @return
     */
    @Bean
    public Queue insertQueue() {
        return new Queue(MqConstants.HOTEL_INSERT_QUEUE, true);
    }

    /**
     * 定义删除的队列
     * @return
     */
    @Bean
    public Queue deleteQueue() {
        return new Queue(MqConstants.HOTEL_DELETE_QUEUE, true);
    }

    /**
     * 定义插入的绑定关系
     * @return
     */
    @Bean
    public Binding insertQueueBinding() {
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(MqConstants.HOTEL_INSERT_KEY);
    }

    /**
     * 定义删除的绑定关系
     * @return
     */
    @Bean
    public Binding deleteQueueBinding() {
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(MqConstants.HOTEL_DELETE_KEY);
    }
}
