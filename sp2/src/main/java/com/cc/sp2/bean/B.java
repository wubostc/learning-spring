package com.cc.sp2.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("BBB")
public class B {

    @Autowired
    public A a;

    public B() {
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
