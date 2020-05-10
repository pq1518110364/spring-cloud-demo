package com.state;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 有限状态机
 *
 * @author xiangwei
 * @date 2020-05-10 8:23 下午
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.bishop.common","com.state"})
public class MachineApp {
    public static void main(String[] args) {
        SpringApplication.run(MachineApp.class, args);
    }
}
