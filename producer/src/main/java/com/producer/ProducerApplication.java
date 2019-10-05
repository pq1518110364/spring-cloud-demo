package com.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.bishop.common","com.producer"})
public class ProducerApplication {
    public static void main(String[] args) {

        SpringApplication.run(ProducerApplication.class, args);
    }
}
