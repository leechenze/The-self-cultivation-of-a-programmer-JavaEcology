package com.lee.mq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    /**
     * FanoutExchange 发布订阅模型：广播队列
     */
    // leechenze.fanout
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("leechenze.fanout");
    }

    // fanout.queue1
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    // 绑定队列1到交换机
    @Bean
    public Binding fanoutBinding1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    // fanout.queue2
    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanout.queue2");
    }

    // 绑定队列2到交换机
    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    /**
     * 消息转换器演示：因为在listener.SpringRabbitListener中基于注解声明消息直接被消费了，所以在这里声明@Bean的方式。
     * @return
     */
    @Bean
    public Queue objectQueue() {
        return new Queue("object.queue");
    }
}
