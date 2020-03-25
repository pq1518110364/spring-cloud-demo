package com.jms.rabbitmq.sender;

import com.bishop.common.utils.JsonUtil;
import com.jms.rabbitmq.config.TestModel;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 9:44
 * @Description:发送消息
 */
@Component
public class SenderMessage {

    @Autowired
    RabbitTemplate rabbitTemplate;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public void test() throws IOException {
        String sss = JsonUtil.toJson(new TestModel(atomicInteger + "", "sss"));
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("rabbit_test_exchange","rabbit_test",sss,correlationData);
        atomicInteger.getAndIncrement();
    }

    public void fanout() throws IOException {
        String sss = JsonUtil.toJson(new TestModel(atomicInteger + "", "fanout"));
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("rabbit_test_fanout","",sss,correlationData);
    }
}
