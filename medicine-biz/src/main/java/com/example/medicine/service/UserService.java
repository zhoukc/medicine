package com.example.medicine.service;

import com.example.medicine.config.ChooseDataSource;
import com.example.medicine.config.DataSources;
import com.example.medicine.expands.ResultExtensions;
import com.example.medicine.ibiz.IUserService;
import com.example.medicine.model.expands.MapperUtil;
import com.example.medicine.model.po.User;
import com.example.medicine.model.ro.GetUsersOutput;
import com.example.medicine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public GetUsersOutput getUsers() {
        List<User> users = userRepository.getUsers();
        List<GetUsersOutput.User> collect = users.stream().map(p -> MapperUtil.mapper(p, GetUsersOutput.User.class)).collect(Collectors.toList());
        return ResultExtensions.ToSucceededResult(collect, GetUsersOutput.class);
    }
}
