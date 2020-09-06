package com.mydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiangwei
 * @date 2020-08-19 10:23 上午
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.bishop.common","com.mydemo"})
public class AMQPApp {
    public static void main(String[] args) {

        SpringApplication.run(AMQPApp.class, args);
    }

}

