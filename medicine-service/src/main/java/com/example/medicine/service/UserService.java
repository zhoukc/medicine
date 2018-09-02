package com.example.medicine.service;

import com.example.medicine.dao.IUserDao;
import com.example.medicine.dao2.IUserDao2;
import com.example.medicine.iservice.IUserService;
import com.example.medicine.model.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserDao2 userDao2;

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int addUser2(User user) {
        return userDao2.addUser(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
