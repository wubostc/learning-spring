package com.scs.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@Configuration
//@LoadBalancerClient(name = "user-service")
//public class BeanConfiguration {
//    @Bean
//    @LoadBalanced
//
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//}
