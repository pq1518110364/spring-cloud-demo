package com.bishop.easyexcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/3/24 17:18
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.bishop.*"})
public class EasyExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyExcelApplication.class, args);
    }
}
