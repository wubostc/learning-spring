package com.scs.service;

import com.scs.common.entity.User;

public interface UserService {
    User getUserById(int id);

    Integer getRemain(Integer uid);

    Boolean setRemain(Integer uid, Integer count);
}
