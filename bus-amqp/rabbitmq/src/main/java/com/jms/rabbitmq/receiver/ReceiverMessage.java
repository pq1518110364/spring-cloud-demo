package com.jms.rabbitmq.receiver;


import com.bishop.common.utils.JsonUtil;
import com.jms.rabbitmq.config.TestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 9:51
 * @Description:
 */
@Component
@Slf4j
public class ReceiverMessage {
    @RabbitListener(bindings = {@QueueBinding(value = @Queue("rabbit_test_queue"),
            exchange = @Exchange("rabbit_test_exchange"), key = "rabbit_test")})
    public void receiveQueue(String message) throws IOException {
        log.info(message);
        TestModel testModel = JsonUtil.toBean(message, TestModel.class);
        log.info(testModel.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(bindings = {@QueueBinding(value=@Queue(value = "rabbit_test_fanout"),
            exchange = @Exchange(value = "rabbit_test_fanout",type = ExchangeTypes.FANOUT))})
    public void receiveAllMessage(Object o) {
        log.info(o.toString());
    }

}
