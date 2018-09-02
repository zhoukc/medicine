package com.example.medicine.dao2;

import com.example.medicine.model.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao2 {

    @Insert("insert into user values(#{name},#{age},#{sex})")
    int addUser(User user);

    @Select("select * from user")
    List<User> getUsers();

}
