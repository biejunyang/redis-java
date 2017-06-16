package com.bjy.redis;

import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {
	
	private static String REDIS_HOST="192.168.73.111";
	private static int REDIS_PORT=6379;
	private static Jedis jedis=null;
	
	
	/**
	 * init redis connection
	 */
	@BeforeClass
	public static void init(){
		jedis=new Jedis(REDIS_HOST, REDIS_PORT);
	}
	
	/**
	 * operate redis string values
	 */
	@Test
	public void testString(){
		jedis.set("name", "张三");
		System.out.println(jedis.get("name"));
		
		jedis.append("name", " is my love");
		System.out.println(jedis.get("name"));
		
		jedis.del("name");
		
		jedis.mset("name", "zangsan", "age", "20", "sex","WOMAN");
		System.out.println(jedis.mget("name","age"));
		jedis.incrBy("age", 5);
		
		System.out.println(jedis.mget("name","age"));
	}
	
	/**
	 * operate redis hash values
	 */
	@Test
	public void testHash(){
		jedis.hset("person1", "name", "zhagnsan");
		jedis.hset("person1", "age", "30");
		jedis.hset("person1", "sex", "WOMAN");
		
		System.out.println(jedis.hmget("person1", "name", "age", "sex"));
		
		for(Iterator<String> iterator=jedis.hkeys("person1").iterator(); iterator.hasNext(); ){
			String key=iterator.next();
			System.out.println(key+":"+jedis.hget("person1", key));
		}
	}
	
	/**
	 * operate redis list vlaues
	 */
	@Test
	public void testList(){
		String key="java frameworks";
		jedis.del(key);
		
		//在表头插入元素
		jedis.lpush(key, "spring");
		jedis.lpush(key, "struts");
		jedis.lpush(key, "hibernate");
		
		//在表尾插入元素
		jedis.rpush(key, "struct2");
		jedis.rpush(key, "mybatis");
		System.out.println(jedis.lrange(key,0,-1));  
		
		System.out.println(jedis.lindex(key, 0)); 
	}
	
	
	/**
	 * operate redis list values
	 */
	@Test
	public void testSet(){
		String key="users";
		jedis.del(key);
		
		jedis.sadd(key, "zhangsan", "lisi", "wangwu", "zhouliu");
		System.out.println(jedis.sismember(key, "zhangsan"));
		System.out.println(jedis.smembers(key));
		System.out.println(jedis.srandmember(key));
		System.out.println(jedis.srandmember(key));
		System.out.println(jedis.scard(key));
	}
	
	
	
	
	@Test
    public void testRedisPool() {
        RedisUtil.getJedis().set("newname", "中文测试");
        System.out.println(RedisUtil.getJedis().get("newname"));
    }
	
	
	@AfterClass
	public static void destroy(){
		jedis.quit();
	}
	
	
}
