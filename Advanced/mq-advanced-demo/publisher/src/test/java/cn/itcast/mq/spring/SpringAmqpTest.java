package cn.itcast.mq.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {
        // 准备发送的消息
        String message = "hello, spring amqp!";
        // 准备RoutingKey
        String routingKey = "simple.test";
        // 准备CorrelationData
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 准备ConfirmCallback（这里可以用lambda表达式来表示，但是为了能看清参数这里就不用lambda的方式了）
        correlationData.getFuture().addCallback(new SuccessCallback<CorrelationData.Confirm>() {
            @Override
            public void onSuccess(CorrelationData.Confirm confirm) {
                // 判断结果
                if (confirm.isAck()) {
                    // ACK
                    log.debug("消息投递到交换机成功！消息ID：{}", correlationData.getId());
                } else {
                    // NACK
                    log.error("消息投递到交换机失败！消息ID：{}", correlationData.getId());
                }
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                // 记录日志
                log.error("消息投递到交换机成功，但是路由到队列失败", throwable);
                // 重发消息

            }
        });

        // 发送消息
        rabbitTemplate.convertAndSend("amq.topic", routingKey, message, correlationData);

    }
}
