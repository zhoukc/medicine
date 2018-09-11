package com.example.medicine.rest;

import com.example.medicine.exception.BusinessException;
import com.example.medicine.ibiz.IUserService;
import com.example.medicine.model.po.User;
import com.example.medicine.model.ro.AddUserInput;
import com.example.medicine.model.ro.AddUserOutput;
import com.example.medicine.model.ro.GetUsersOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class TestController {

//    @Autowired
//    @Qualifier(value = "redisCacheTemplate")
//    private RedisTemplate redisTemplate;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    // private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello springboot multi-module";
    }


    @PostMapping(value = "addUser", produces = "application/json")
    public AddUserOutput addUser(@Validated @RequestBody AddUserInput input) {
        return userService.addUser(input);
    }


    @RequestMapping("/add")
    public String add() {
        User user = new User();
        user.setAge(20);
        user.setName("jack");
        user.setSex("M");
        int result = 0;
        if (result == 0) {
            throw new BusinessException(1, "id不存在");
        }
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
//        redisTemplate.opsForValue().set("key2", "com.example.medicine.rest.TestController");
//        stringRedisTemplate.opsForValue().set("string2", "org.springframework.boot.web.embedded.tomcat.TomcatWebServer");
//
//        stringRedisTemplate.opsForValue().set("u2", "com.zaxxer.hikari.HikariDataSource", 60, TimeUnit.SECONDS);
        return userService.getUsers();
    }
}
