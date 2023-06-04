package cn.itcast.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorMessageConfig {

    /**
     * 定义异常消息的交换机
     */
    @Bean
    public DirectExchange errorMessageExchange() {
        return new DirectExchange("error.direct");
    }

    /**
     * 定义异常消息队列
     */
    @Bean
    public Queue errorQueue() {
        return new Queue("error.queue");
    }

    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean
    public Binding errorMessageBinding() {
        return BindingBuilder.bind(errorQueue()).to(errorMessageExchange()).with("error.key");
    }

    /**
     * 异常消息处理器（消费者失败消息的处理策略）
     * @param rabbitTemplate
     * @return
     */
    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
        // 参数分别为：rabbitTemplate，异常消息的交换机，异常消息的RoutingKey。
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error.key");
    }

}
