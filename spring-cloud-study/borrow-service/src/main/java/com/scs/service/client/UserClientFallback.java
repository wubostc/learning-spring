package com.scs.service.client;

import com.scs.common.entity.User;
import com.scs.common.error.MyBizError;
import com.scs.common.exception.MyBusinessException;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {
    @Override
    public User getUserById(Integer id) {

        throw new MyBusinessException(MyBizError.FLOW_LIMITING, "The user-service is unavailable");

        // 当user-service宕机后，返回替代方案
//        User user = new User();
//        user.setName("null");
//        user.setUid(id);
//        return user;
    }

    @Override
    public Integer getRemain(Integer uid) {
        return 0;
    }

    @Override
    public Boolean userBorrow(Integer uid) {
        return false;
    }
}
