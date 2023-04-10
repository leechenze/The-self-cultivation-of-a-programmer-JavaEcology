package com.lee.mq;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


}
