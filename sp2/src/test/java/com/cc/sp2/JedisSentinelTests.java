package com.cc.sp2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.resps.Tuple;

import java.util.*;

@SpringBootTest
class JedisSentinelTests {
	private JedisPooled jedis = new JedisPooled("127.0.0.1", 6379);

	private JedisSentinelPool sentinels;
	{
		HashSet<String> set = new HashSet<>();
		set.add("127.0.0.1:26379");
		set.add("127.0.0.1:26380");
		set.add("127.0.0.1:26381");
		sentinels = new JedisSentinelPool("mymaster", set);
		System.out.println("sentinels = " + sentinels);
	}

	@Test
	public void test01(){

			jedis.set("name", "zs");
			System.out.println("name = " + jedis.get("name"));

			jedis.hset("emp", "name", "哦哦哦");
			Map<String, String> map = new HashMap<>();
			map.putIfAbsent("age", "66");
			System.out.println(map.putIfAbsent("age", "7"));
			jedis.hset("emp", map);
			System.out.println(jedis.hgetAll("emp"));

	}


	@Test
	public void test03() {

			System.out.println(jedis.rpush("cities", "北京", "广州", "上海", "深圳"));

			System.out.println(jedis.lrange("cities", 0, -1));


	}


	@Test
	public void test04() {
			System.out.println(jedis.sadd("courses", "python", "RocketMQ", "Redis", "Kafka"));
			Set<String> courses = jedis.smembers("courses");
			System.out.println("courses = " + courses);

	}

	@Test
	public void test05() {
			jedis.zadd("sales", 50, "Benz");
			jedis.zadd("sales", 40, "BMW");
			jedis.zadd("sales", 30, "Audi");
			jedis.zadd("sales", 80, "Tesla");
			jedis.zadd("sales", 180, "BYD");

			List<String> top3 = jedis.zrevrange("sales", 0, 2);

			System.out.println("top3 = " + top3);

			List<Tuple> sales = jedis.zrevrangeWithScores("sales", 0, -1);

			for (Tuple sale : sales) {
				System.out.println(sale.getElement() + ":" + sale.getScore());
			}

			System.out.println("sales = " + sales);

	}
}
