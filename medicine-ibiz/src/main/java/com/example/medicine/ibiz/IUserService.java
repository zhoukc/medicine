package com.example.medicine.ibiz;

import com.example.medicine.model.po.User;
import com.example.medicine.model.ro.AddUserInput;
import com.example.medicine.model.ro.AddUserOutput;
import com.example.medicine.model.ro.GetUsersOutput;

import java.util.List;


public interface IUserService {

    AddUserOutput addUser(AddUserInput input);

    int addUser2(User user);

    GetUsersOutput getUsers();
}
