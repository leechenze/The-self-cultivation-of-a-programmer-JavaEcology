package com.lee.mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

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


    /**
     * FanoutExchange 发布订阅模型：广播队列
     * @param msg
     */
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) {
        System.out.println("消费者接收到fanout.queue1的消息：【" + msg + "】");
    }
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) {
        System.out.println("消费者接收到fanout.queue2的消息：【" + msg + "】");
    }


    /**
     * DirectExchange 发布订阅模型：路由队列
     *
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "leechenze.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者接收到direct.queue1的消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "leechenze.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String msg) {
        System.out.println("消费者接收到direct.queue2的消息：【" + msg + "】");
    }


    /**
     * TopicExchange 发布订阅模型：话题队列
     * china.news
     * china.weather
     * japan.news
     * jspan.weather
     *
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "leechenze.topic", type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenTopicQueue1(String msg) {
        System.out.println("消费者接收到topic.queue1的消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "leechenze.topic", type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenTopicQueue2(String msg) {
        System.out.println("消费者接收到topic.queue2的消息：【" + msg + "】");
    }


    /**
     * 消息转换器演示：监听object.queue
     *
     * @param msg
     */
    @RabbitListener(queues = "object.queue")
    public void listenObjectQueue(Map<String, Object> msg) {
        System.out.println("消费者接收到 object.queue 的消息：【" + msg + "】");
    }

}
