package com.example.medicine.service;

import com.alibaba.fastjson.JSON;
import com.example.medicine.MedicineWebApplication;
import com.example.medicine.model.ro.GetUsersOutput;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * UserService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 6, 2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MedicineWebApplication.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * Method: addUser(User user)
     */
    @Test
    public void testAddUser() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addUser2(User user)
     */
    @Test
    public void testAddUser2() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getUsers()
     */
    @Test
    public void testGetUsers() throws Exception {

        GetUsersOutput output = userService.getUsers();
        System.out.println(JSON.toJSONString(output));
    }


} 
