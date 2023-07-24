package com.cc.sp2.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

@Component("AAA")

public class A {

    @Autowired
    public B b;

    public A() {
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }


}
