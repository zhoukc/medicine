package com.example.medicine.service;

import com.example.medicine.common.mapper.CGlibMapper;
import com.example.medicine.config.ChooseDataSource;
import com.example.medicine.config.DataSources;
import com.example.medicine.expands.ResultExtensions;
import com.example.medicine.ibiz.IUserService;
import com.example.medicine.model.expands.MapperUtil;
import com.example.medicine.model.po.User;
import com.example.medicine.model.ro.AddUserInput;
import com.example.medicine.model.ro.AddUserOutput;
import com.example.medicine.model.ro.GetUsersOutput;
import com.example.medicine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddUserOutput addUser(AddUserInput input) {
        User user = MapperUtil.mapper(input, User.class);
        int i = userRepository.addUser(user);
        return ResultExtensions.ToFailedResult(AddUserOutput.FailureReasons.REASONS, AddUserOutput.class);
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
