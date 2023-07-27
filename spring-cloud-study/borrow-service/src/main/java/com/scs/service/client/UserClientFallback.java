package com.scs.service.client;

import com.scs.common.entity.User;
import com.scs.common.error.MyBizError;
import com.scs.common.exception.MyBusinessException;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {
    @Override
    public User getUserById(Integer id) {
        throw new MyBusinessException(MyBizError.HYSTRIX_RUNTIME_EXCEPTION);
//        User user = new User();
//        user.setName("这是补救措施，userservice 已崩溃");
//        user.setUid(id);
//        return user;
    }
}
