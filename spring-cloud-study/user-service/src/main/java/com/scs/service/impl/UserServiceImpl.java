package com.scs.service.impl;

import com.scs.common.entity.User;
import com.scs.mapper.UserMapper;
import com.scs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Value("${hello}")
    String hello;

    @Override
    public User getUserById(int id) {
        System.out.println("id = " + id + " " + hello);

        return userMapper.getUserById(id);
    }

    @Override
    public Integer getRemain(Integer uid) {
        return userMapper.getUserBookRemain(uid);
    }

    @Override
    public Boolean setRemain(Integer uid, Integer count) {
        return userMapper.updateBookCount(uid, count);
    }
}
