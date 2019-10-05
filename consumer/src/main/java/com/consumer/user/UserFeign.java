package com.consumer.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "producer")
public interface UserFeign  {

    @GetMapping("/producer/user/test")
    public String test();
}
