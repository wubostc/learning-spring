package com.demo.c01.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


@Component
public class Dog implements FactoryBean<Dog> {


    private String a;


    @Override
    public Dog getObject() throws Exception {
        Dog dog = new Dog();
        dog.a = "111";
        return dog;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + a + '\'' +
                '}';
    }

    @Override
    public Class<?> getObjectType() {
        return Dog.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
