package cn.itcast.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg) {
        // 这里改为日志记录方法打印，方便看时间是否与配置匹配。
        log.debug("消费者接收到simple.queue的消息：【" + msg + "】");
        // 此处打断点进行测试，并编写一个异常。
        System.out.println(1 / 0);
        log.info("消费者处理消息成功");
    }

}
