package com.bishop.spring.boot.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/1/6 11:07
 * @Description:
 */
@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminServerApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminServerApp.class, args);
    }
}
