package com.demo.c01.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFPP implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFPP");
        var str = """
                the name changed by MyBeanFPP
                🤣
                """;
        beanFactory.getBeanDefinition("User").getPropertyValues().add("name", str);
    }
}
