package cn.itcast.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringRabbitListener {

    // @RabbitListener(queues = "simple.queue")
    // public void listenSimpleQueue(String msg) {
    //     // 这里改为日志记录方法打印，方便看时间是否与配置匹配。
    //     log.debug("消费者接收到simple.queue的消息：【" + msg + "】");
    //     // 此处打断点进行测试，并编写一个异常。
    //     System.out.println(1 / 0);
    //     log.info("消费者处理消息成功");
    // }

    /**
     * 死信交换机和死信队列，基于注解的方式
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dl.queue", durable = "true"),
            exchange = @Exchange(name = "dl.direct"),
            key = "dl.key"
    ))
    public void listenDLQueue(String msg) {
        log.info("消费者接收到了dl.queue的延迟消息：" + msg);
    }

    // /**
    //  * Rabbit插件实现延迟消息：rabbitmq_delayed_message_exchange
    //  * 指定交换机为延迟的：delayed = "true"
    //  * @param msg
    //  */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(name = "delay.queue", durable = "true"),
    //         exchange = @Exchange(name = "delay.direct", delayed = "true"),
    //         key = "delay.key"
    // ))
    // public void listenDelayExchange(String msg) {
    //     log.info("消费者接收到了delay.queue的延迟消息：" + msg);
    // }

}
