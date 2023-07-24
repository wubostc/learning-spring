package com.cc.sp2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class JedisTxTests {
	private JedisPool pool = new JedisPool("127.0.0.1", 6380);

	@Test
	public void test01(){
		Jedis jedis = pool.getResource();


		Transaction tx = jedis.multi();
		try {
			tx.set("name", "...");
			Integer i = 1 / 0;
			tx.set("age", "80");
			tx.exec();
		} catch (Exception e) {
			tx.discard();
			e.printStackTrace();
		} finally {
			System.out.println(jedis.get("name"));
			System.out.println(jedis.get("age"));
		}

	}


}
