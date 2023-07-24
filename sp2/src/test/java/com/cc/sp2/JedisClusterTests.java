package com.cc.sp2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.*;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.resps.Tuple;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.util.*;

@SpringBootTest
class JedisClusterTests {
//	private JedisPooled jedis = new JedisPooled("127.0.0.1", 6379);

	private JedisCluster jedis;
	{
		Set<HostAndPort> s = new HashSet<>();
		String host = "192.168.180.166";
		s.add(new HostAndPort(host, 6380));
		s.add(new HostAndPort(host, 6381));
		s.add(new HostAndPort(host, 6382));
		s.add(new HostAndPort(host, 6383));
		s.add(new HostAndPort(host, 6384));
		s.add(new HostAndPort(host, 6385));
		jedis = new JedisCluster(s);

	}

	@Test void test01() {
		System.out.println("""
				jedis.get(hello) =\s""" + jedis.get("hello"));

		Map<String, String> map = new HashMap<>();
		map.put("shanghai", "[121.480539, 31.235929]");
		map.put("hangzhou", "[120.215512, 30.253083]");
		map.put("neimengguzizhiqu", "[111.772606, 40.823156]");
		jedis.hset("cities_geo", map);

		Map<String, String> citiesGeo = jedis.hgetAll("cities_geo");
		System.out.println("citiesGeo = " + citiesGeo);


		jedis.mset("name{JedisClusterTest}", "Jojo", "age{JedisClusterTest}", "80");
		System.out.println("name and age = " + jedis.mget("name{JedisClusterTest}", "age{JedisClusterTest}"));
	}

	@Test
	public  void test02() {
		Map<String, ConnectionPool> clusterNodes = jedis.getClusterNodes();
//		jedis.
//		jedis.getConnectionFromSlot()
		System.out.println("clusterNodes = " + clusterNodes);
//		ConnectionPool connectionPool = clusterNodes.get("192.168.180.166:6380");

		Map<String, List<Long>> m = new HashMap<>();

		Set<String> s = clusterNodes.keySet();
		Iterator<String> it = s.iterator();

		if (it.hasNext()) {
			String host = it.next();
			var r = clusterNodes.get(host).getResource();



//			ClusterCommandArguments
			CommandArguments args = new CommandArguments(Protocol.Command.CLUSTER);
			args.addObjects(Protocol.ClusterKeyword.SLOTS);

			List<List<Object>> result = (List<List<Object>>) r.executeCommand(args);
//			String string = r.getOne().toString();

//			System.out.println(o);


			for (List<Object> list : result) {
				List<Long> slots = new ArrayList<>(2);

				var slotStart = (Long) list.get(0);
				var slotEnd = (Long) list.get(1);
				slots.add(slotStart);
				slots.add(slotEnd);

				var node1 = (ArrayList<Object>)list.get(2);
				var node2 = (ArrayList<Object>)list.get(3);
				var host1 = new String((byte[]) node1.get(0));
				var port1 = (Long) node1.get(1);
				var host2 = new String((byte[]) node2.get(0));
				var port2 = (Long) node2.get(1);

				m.put(host1 + ":" + port1, slots);
				m.put(host2 + ":" + port2, slots);
			}
		}

		int slot = JedisClusterCRC16.getSlot("hello");

		for (String host : s) {
			List<Long> slots = m.get(host);
			if (slots.get(0) <= slot || slot <= slots.get(1)) {
				Connection r = clusterNodes.get(host).getResource();

				CommandArguments args = new CommandArguments(Protocol.Command.WATCH);
				args.addObjects("hello");
				r.executeCommand(args);


				Transaction tx = new Transaction(r);
				try {
					tx.multi();
					tx.set("hello", "hi");
					tx.exec();
				} catch (Exception e) {
					e.printStackTrace();
					tx.discard();
				} finally {
					tx.close();
				}

				Object o = r.executeCommand(Protocol.Command.EXEC);

			}
		}



		clusterNodes.forEach((k, v) -> {



//			m.put(k, )
		});

//		Connection res = connectionPool.getResource();
//		connectionPool.getResource().sendCommand(Protocol.Command.MULTI);

//		connectionPool.getResource().executeCommand();
//		JedisPool a = new JedisPool("");
//		a.getResource().multi();

//		Jedis j;
//		JedisClusterCRC16.getCRC16();
	}

}
