package com.cc.sp2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

//@ComponentScan(basePackages = "com.cc.sp2")
//@ComponentScan(basePackages = "com.cc.sp2")
@Component("testBean")
public class Test implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("1111111111111222");
    }
}
