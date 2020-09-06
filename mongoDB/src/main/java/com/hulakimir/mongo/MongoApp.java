package com.hulakimir.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiangwei
 * @date 2020-09-03 2:11 下午
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.bishop.common","com.hulakimir"})
public class MongoApp {

    public static void main(String[] args) {
        SpringApplication.run(MongoApp.class, args);    }
}
