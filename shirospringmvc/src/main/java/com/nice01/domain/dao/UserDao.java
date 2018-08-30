package com.nice01.domain.dao;

import com.nice01.domain.User;

import java.util.List;

public interface UserDao {


    User getUserByUserName(String name);


    List<String> queryRolesByUserName(String userName);

}
