package com.dhcc.service.impl;

import com.dhcc.entity.User;
import com.dhcc.mapper.UserMapper;
import com.dhcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

}
