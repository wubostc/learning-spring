package com.cc.sp2;

import com.cc.sp2.bean.A;
import com.cc.sp2.bean.B;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class Sp2Application {

	@Value("${spring.redis.port}")
	private Integer redisPort;

	@Value("${spring.redis.host}")
	private String redisHost;

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(Sp2Application.class, args);
		A aaa = (A) ctx.getBean("AAA");
		B bbb = (B) ctx.getBean("BBB");

		System.out.println("aaa = " + aaa);
		System.out.println("bbb = " + bbb);
		System.out.println("aaa.getB() = " + aaa.getB());
		System.out.println("bbb.getA() = " + bbb.getA());

		List<Integer> list = Stream.of(1, 2, 3).collect(Collectors.toList());


	}


	@Bean(name = "111")
	public Redisson myredisson() {
		Config cfg = new Config();
		cfg.useSingleServer()
				.setAddress("redis://" + redisHost + ":" + redisPort)
				.setDatabase(0);
		return (Redisson) Redisson.create(cfg);
	}

//	@Bean(name = "222")
//	public Redisson myredisson2() {
//		Config cfg = new Config();
//		cfg.useSingleServer()
//				.setAddress("redis://" + redisHost + ":" + redisPort)
//				.setDatabase(0);
//		return (Redisson) Redisson.create(cfg);
//	}


//	@Bean(name = "redisson-1")
//	public Redisson redisson1() {
//		Config cfg = new Config();
//		cfg.useSentinelServers()
//				.setMasterName("mymaster1")
//				.addSentinelAddress("127.0.0.1:26380", "127.0.0.1:26381", "127.0.0.1:26382");
//		return (Redisson) Redisson.create(cfg);
//	}
//	@Bean(name = "redisson-2")
//	public Redisson redisson2() {
//		Config cfg = new Config();
//		cfg.useSentinelServers()
//				.setMasterName("mymaster1")
//				.addSentinelAddress("127.0.0.1:36380", "127.0.0.1:36381", "127.0.0.1:36382");
//		return (Redisson) Redisson.create(cfg);
//	}
//	@Bean(name = "redisson-3")
//	public Redisson redisson3() {
//		Config cfg = new Config();
//		cfg.useSentinelServers()
//				.setMasterName("mymaster1")
//				.addSentinelAddress("127.0.0.1:46380", "127.0.0.1:46381", "127.0.0.1:46382");
//		return (Redisson) Redisson.create(cfg);
//	}
}
