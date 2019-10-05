package com.consumer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserFeign userFeign;

    public String findUser(){
        return  userFeign.test();
    }
}
