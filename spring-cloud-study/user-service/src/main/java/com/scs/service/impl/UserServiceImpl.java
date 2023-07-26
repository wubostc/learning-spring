package com.scs.service.impl;

import com.scs.common.entity.User;
import com.scs.mapper.UserMapper;
import com.scs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(int id) {
        System.out.println("id = " + id);

        return userMapper.getUserById(id);
    }
}
