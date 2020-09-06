package com.hulakimir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiangwei
 * @date 2020-08-29 9:48 上午
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.bishop.common","com.hulakimir"})
public class MultiThreadingApp {

    public static void main(String[] args) {
        SpringApplication.run(MultiThreadingApp.class, args);    }
}
