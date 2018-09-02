package com.example.medicine.repository;

import com.example.medicine.config.ChooseDataSource;
import com.example.medicine.config.DataSources;
import com.example.medicine.dao.IUserDao;
import com.example.medicine.model.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private IUserDao userDao;


    @ChooseDataSource(DataSources.MEDICINE)
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @ChooseDataSource(DataSources.MEDICINE)
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @ChooseDataSource(DataSources.MEDICINE2)
    public int addUser2(User user) {
        return userDao.addUser(user);
    }

}
