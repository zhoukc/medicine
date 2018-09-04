package com.example.medicine.rest;

import com.example.medicine.ibiz.IUserService;
import com.example.medicine.model.po.User;
import com.example.medicine.model.ro.GetUsersOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class TestController {

    // private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello springboot multi-module";
    }


    @RequestMapping("/add")
    public String add() {
        User user = new User();
        user.setAge(20);
        user.setName("jack");
        user.setSex("M");
        int result = userService.addUser(user);

        User user2 = new User();
        user2.setAge(20);
        user2.setName("mary");
        user2.setSex("F");

        result += userService.addUser2(user2);

        return "data add success! result=" + result;
    }

    @RequestMapping("/users")
    public GetUsersOutput getUsers() {
        log.info("访问getUsers");
        return userService.getUsers();
    }
}
