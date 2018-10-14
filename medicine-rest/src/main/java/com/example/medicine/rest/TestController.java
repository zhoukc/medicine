package com.example.medicine.rest;

import com.example.medicine.exception.BusinessException;
import com.example.medicine.ibiz.IUserService;
import com.example.medicine.model.expands.ApiDescription;
import com.example.medicine.model.po.User;
import com.example.medicine.model.ro.AddUserInput;
import com.example.medicine.model.ro.AddUserOutput;
import com.example.medicine.model.ro.GetUsersOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@Api(tags = "测试Controller")
public class TestController {


    @Autowired
    private IUserService userService;

    @ApiOperation(value = "hello方法", notes = "测试方法")
    @GetMapping(value = "/hello")
    public String hello() {
        return "hello springboot multi-module";
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PostMapping(value = "addUser", produces = "application/json", headers = {"token"})
    public AddUserOutput addUser(@Validated @RequestBody AddUserInput input) {
        return userService.addUser(input);
    }


    @ApiOperation(value = "添加用户")
    @GetMapping("/add")
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

    @ApiOperation(value = "获取用户列表")
    @GetMapping("/users")
    public GetUsersOutput getUsers() {
        return userService.getUsers();
    }
}
