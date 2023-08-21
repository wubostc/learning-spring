package com.scs.config;

import com.scs.dao.SecurityUserDao;
import com.scs.entity.SecurityRole;
import com.scs.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Component
public class MyUserDetailService implements UserDetailsService {

    private final SecurityUserDao securityUserDao;

    @Autowired
    public MyUserDetailService(SecurityUserDao securityUserDao) {
        this.securityUserDao = securityUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        SecurityUser user = securityUserDao.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) throw new UsernameNotFoundException("用户不存在: " + username);

        // 查询权限
        List<SecurityRole> roles = securityUserDao.getRolesByUid(user.getId());

        user.setRoles(roles);

        return user;
    }
}
