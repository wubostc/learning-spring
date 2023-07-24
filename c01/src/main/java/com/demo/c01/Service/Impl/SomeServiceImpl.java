package com.demo.c01.Service.Impl;

import com.demo.c01.Service.SomeService;
import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService {
    @Override
    public void query(Integer id) {
        System.out.println("query ===== ");
        Class<SomeService> someServiceClass = SomeService.class;

//        someServiceClass.getDeclaredConstructor().newInstance()
    }

    @Override
    public void save(String name, String code) {
        System.out.println("save ==== = ");
    }
}
