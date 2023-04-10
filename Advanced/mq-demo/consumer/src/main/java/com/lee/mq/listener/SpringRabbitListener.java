package com.lee.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class SpringRabbitListener {
    /**
     * BasicQueue 基础队列模型
     * @param msg
     */
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg) {
        System.out.println("消费者接收到simple.queue的消息：【" + msg + "】");
    }


    /**
     * WorkQueue 工作队列模型
     * @param msg
     */
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        // 为了和工作队列2区别，这里使用 out 打印普通日志
        System.out.println("工作队列模型消费者1 收到work.queue的消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(20);
    }
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        // 为了和工作队列1区别，这里使用 err 打印红色错误日志
        System.err.println("工作队列模型消费者2 收到work.queue的消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(200);
    }

}
