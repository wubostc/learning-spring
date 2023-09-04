package com.cc.sp2.service.impl;

import com.cc.sp2.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class testServiceImpl implements TestService {
    @Transactional
    @Override
    public String handleTest() {
        return "null;";
    }
}
