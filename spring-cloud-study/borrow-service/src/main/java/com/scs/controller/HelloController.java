package com.scs.controller;

import com.scs.annotation.RespNotInterceptor;
import com.scs.common.entity.User;
import com.scs.entity.UserBorrowDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("")
@Slf4j
public class HelloController {


    @Autowired
    AuthenticationManager authenticationManager;

    @RespNotInterceptor
    @GetMapping("/hello")
    public String handleHello() {
        System.out.println("authenticationManager = " + authenticationManager);
        System.out.println("hello security");
        log.debug("h");

        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println("身份信息：" + context.getAuthentication().getPrincipal());
        System.out.println("授权信息：" + context.getAuthentication().getAuthorities());
        System.out.println("凭据：" + context.getAuthentication().getCredentials());

        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(1);

        System.out.println("------------------");
        new Thread(() -> {
            SecurityContext context2 = SecurityContextHolder.getContext();
            System.out.println("身份信息：" + context2.getAuthentication().getPrincipal());
            System.out.println("授权信息：" + context2.getAuthentication().getAuthorities());
            System.out.println("凭据：" + context2.getAuthentication().getCredentials());
        }).start();

        return "hello spring security";
    }


    @GetMapping("/hello2")
    public Map<String, Object> handleHello2() {
        System.out.println("hello security");
        log.debug("h");
        HashMap<String, Object> map = new HashMap<>();

        map.put("hello 2", "hello spring security");
        return map;
    }

    @GetMapping("/hello3")
    public List<Object> handleHello3() {
        ArrayList<Object> arr = new ArrayList<>(10);
        arr.add("你好");

        User user = new User();
        user.setUid(111);
        user.setSex("女");
        UserBorrowDetail userBorrowDetail = new UserBorrowDetail(user, new LinkedList<>());
        arr.add(userBorrowDetail);
        arr.add(null);
        arr.add(null);
        arr.add(Math.E);
        return arr;
    }
}
