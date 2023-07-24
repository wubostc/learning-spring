package com.scs.controller;

import com.scs.common.entity.User;
import com.scs.service.UserService;
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
}
