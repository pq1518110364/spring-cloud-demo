package com.producer.user;

import org.springframework.stereotype.Service;

@Service
public class ProducerUserServiceImpl implements ProducerUserService {
    @Override
    public String test() {
        return "success";
    }
}
