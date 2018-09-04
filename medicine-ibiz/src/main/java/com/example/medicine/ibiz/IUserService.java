package com.example.medicine.ibiz;

import com.example.medicine.model.po.User;
import com.example.medicine.model.ro.GetUsersOutput;

import java.util.List;


public interface IUserService {

    int addUser(User user);

    int addUser2(User user);

    GetUsersOutput getUsers();
}
