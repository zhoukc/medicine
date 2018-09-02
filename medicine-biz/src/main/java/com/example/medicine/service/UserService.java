package com.example.medicine.service;

import com.example.medicine.config.ChooseDataSource;
import com.example.medicine.config.DataSources;
import com.example.medicine.ibiz.IUserService;
import com.example.medicine.model.po.User;
import com.example.medicine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public int addUser(User user) {
        return userRepository.addUser(user);
    }

    @ChooseDataSource(DataSources.MEDICINE2)
    @Override
    public int addUser2(User user) {
        return userRepository.addUser(user);
    }


    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }
}
