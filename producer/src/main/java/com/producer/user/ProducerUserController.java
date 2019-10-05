package com.producer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class ProducerUserController {

    @Autowired
    private ProducerUserService producerUserService;

    @GetMapping("/test")
    public String test(){
        return producerUserService.test();
    }
}
