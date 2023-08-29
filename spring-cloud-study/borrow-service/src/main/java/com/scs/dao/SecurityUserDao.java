package com.scs.dao;

import com.scs.entity.SecurityRole;
import com.scs.entity.SecurityUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecurityUserDao {
    // 根据用户名查询用户信息
    SecurityUser loadUserByUsername(String username);


    // 根据用户 uid，查询用户角色
    List<SecurityRole> getRolesByUid(Integer uid);
}
