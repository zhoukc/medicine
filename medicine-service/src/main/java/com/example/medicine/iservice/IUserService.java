package com.example.medicine.iservice;

import com.example.medicine.model.po.User;

import java.util.List;

public interface IUserService {

    int addUser(User user);

    int addUser2(User user);

    List<User> getUsers();
}
