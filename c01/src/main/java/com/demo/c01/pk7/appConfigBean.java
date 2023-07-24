package com.demo.c01.pk7;

import com.demo.c01.Config.Security;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class appConfigBean {

    @ConfigurationProperties(prefix = "security")
    @Bean
    public Security getSSS() {
        return new Security();
    }
}
