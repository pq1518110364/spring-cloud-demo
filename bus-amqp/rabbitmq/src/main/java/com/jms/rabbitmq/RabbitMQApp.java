package com.jms.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 9:28
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.bishop.common","com.jms.rabbitmq"})
@EnableRabbit
public class RabbitMQApp {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApp.class,args);
    }


}
