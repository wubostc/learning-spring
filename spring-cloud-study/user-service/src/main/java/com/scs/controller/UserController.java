package com.scs.controller;

import com.scs.common.entity.User;
import com.scs.service.UserService;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{uid}")
    public User getUserById(@PathVariable("uid") int id) {
        return userService.getUserById(id);
    }


    // 还剩多少可以借阅的数量
    @GetMapping("/user/remain/{uid}")
    public Integer getRemain(@PathVariable("uid") Integer uid) {
        System.out.println("RootContext.getXID() = " + RootContext.getXID());
        return userService.getRemain(uid);
    }

    @GetMapping("/user/borrow/{uid}")
    public Boolean userBorrow(@PathVariable("uid") Integer uid) {
        System.out.println("RootContext.getXID() = " + RootContext.getXID());

        Integer remain = userService.getRemain(uid);
        return userService.setRemain(uid, remain - 1);
    }
}
