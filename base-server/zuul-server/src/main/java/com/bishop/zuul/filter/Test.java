package com.bishop.zuul.filter;

import com.bishop.common.utils.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: bishop
 * Create by Poseidon on 2019-04-22
 */
@RestController
@Slf4j
public class Test {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @GetMapping("/test")
    public void  test(){
        RedisClient.getInstance().lPush("123","345");
        RedisClient.getInstance().lPush("123","197");
        while (Thread.currentThread().isInterrupted()){
            List<byte[]> list=  lettuceConnectionFactory.getConnection().bRPop(100,"123".getBytes());
            if (list!=null){
                for (byte[] bytes: list) {
                    log.info("bytes:{}",new String(bytes));
                }
                Thread.currentThread().interrupt();
            }else {
                log.info("完成了...");
            }
        }
    }

}
