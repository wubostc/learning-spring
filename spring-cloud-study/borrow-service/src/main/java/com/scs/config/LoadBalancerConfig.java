package com.scs.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class LoadBalancerConfig
{
    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer111(Environment environment,
                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        //返回随机轮询负载均衡方式
        return new RandomLoadBalancer(loadBalancerClientFactory.
                getLazyProvider(name, ServiceInstanceListSupplier.class),
                name);

        //轮询加载,默认就是这个,这里作为演示
//        return new RoundRobinLoadBalancer(loadBalancerClientFactory.getLazyProvider(name,
//                ServiceInstanceListSupplier.class),name);

    }
}
