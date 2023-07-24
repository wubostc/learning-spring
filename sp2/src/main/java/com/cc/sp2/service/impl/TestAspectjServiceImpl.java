package com.cc.sp2.service.impl;

import com.cc.sp2.service.TestAspectjService;
import org.springframework.stereotype.Service;

@Service
public class TestAspectjServiceImpl implements TestAspectjService {

    @Override
    public String test01() {
        System.out.println("TestAspectjService test01 ok");
        return null;
    }
}
