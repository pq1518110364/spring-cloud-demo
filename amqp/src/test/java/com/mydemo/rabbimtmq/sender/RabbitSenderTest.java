package com.mydemo.rabbimtmq.sender;

import com.mydemo.AMQPApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author xiangwei
 * @date 2020-08-19 10:31 上午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =AMQPApp.class)
public class RabbitSenderTest {

    @Autowired
    RabbitSender rabbitSender;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void send() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        rabbitSender.send("Hello RabbitMQ For Spring Boot!", properties);
    }
}