package cn.itcast.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CommonConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取RabbitTemplate对象
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 配置ReturnCallback（这里可以用lambda表达式来表示，但是为了能看清参数这里就不用lambda的方式了）
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                // 记录日志：（第一个参数为错误消息，消息中的{}是关键字，会将后续参数挨个匹配到第一个字符串中的{}关键字中去）
                log.error("消息发送到队列失败，响应码：{}, 失败原因：{}, 交换机: {}, 路由Key: {}, 消息：{}", replyCode, replyText, exchange, routingKey, message);
                // 如果有需要的话，重发消息。

            }
        });
    }
}
