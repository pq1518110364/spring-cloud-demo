package com.consumer.user;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public void test() {
        System.out.println(1);
    }


    @GetMapping("/findUser")
    public String findUser() {
        return this.userService.findUser();
    }

    public static void main(String[] args) {
        Map<Integer,String> map = Maps.newHashMap();
        for (int i = 0; i <10 ; i++) {
            map.put(i,""+i);
        }
        for (Map.Entry m:map.entrySet()) {
            System.out.println(m.getKey()+""+m.getValue());
        }
    }

}
