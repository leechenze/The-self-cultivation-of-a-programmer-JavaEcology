package com.lee.mq;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherSpringAMQPTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * BasicQueue 基础队列模型
     */
    @Test
    public void testSendMessage2SimpleQueue() {
        String queueName = "simple.queue";
        String message = "Hello SpringAMQP BasicQueue";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    /**
     * WorkQueue 工作队列模型
     */
    @Test
    public void testSendMessage2WorkQueue() throws InterruptedException {
        String queueName = "work.queue";
        String message = "Hello SpringAMQP WorkQueue --> ";
        for(int i = 0; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
    }


    /**
     * FanoutExchange 发布订阅模型：广播队列
     * @description 不同之处在于之前都是发消息到队列，这里是发消息到交换机，代码也略有不同
     */
    @Test
    public void testSendFanoutExchange() {
        // 交换机名称
        String exchangeName = "leechenze.fanout";
        // 消息定义
        String message = "hello FanoutExchange every one";
        // 消息发送
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }


    /**
     * FanoutExchange 发布订阅模型：广播队列
     * @description 不同之处在于之前都是发消息到队列，这里是发消息到交换机，代码也略有不同
     */
    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "leechenze.direct";
        // 消息定义
        String message = "hello red";
        // 消息发送（这里routingkey要分别尝试改为：red（direct.queue1 && queue2），blue（direct.queue1），yellow（direct.queue2））
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }


    /**
     * TopicExchange 发布订阅模型：广播队列
     */
    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "leechenze.topic";
        // 消息定义
        String message = "中国新闻！！！轰20首飞了，十二万吨核动力航母下水啦！！！";
        // 消息发送（这里routingkey要分别尝试：china.news, china.weather, japan.news, japan.weather）
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
    }


    /**
     * 消息转换器演示
     */
    @Test
    public void testSendObjectQueue() {
        Map<String, Object> msg = new HashMap<>();
        msg.put("name", "trump");
        msg.put("age", 60);
        rabbitTemplate.convertAndSend("object.queue", msg);
    }

}
