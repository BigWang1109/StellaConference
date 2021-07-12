package com.wxx.conference.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * Created by thinkpad on 2020-5-28.
 */
public class RedisPoolUtil {

    private static JedisPool jedisPool = null;
    private static String redisConfigFile = "WEB-INF/redis.properties";

    private static ThreadLocal<Jedis> local = new ThreadLocal<Jedis>();

    private RedisPoolUtil(){

    }
    //初始化线程池
    public static void initPool(){

        try{
            Properties props = new Properties();
            props.load(RedisPoolUtil.class.getClassLoader().getResourceAsStream(redisConfigFile));
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));
            config.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));
            config.setMaxWaitMillis(Long.valueOf(props.getProperty("jedis.pool.maxWait")));
            config.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));
            jedisPool = new JedisPool(config, props.getProperty("redis.ip"),
                    Integer.valueOf(props.getProperty("redis.port")),
                    Integer.valueOf(props.getProperty("redis.timeout")),
                    props.getProperty("redis.passWord"));
            System.out.println("Redis线程池初始化成功");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    //获取连接
    public static Jedis getConn(){
        Jedis jedis = local.get();
        if(jedis == null){
            if(jedisPool == null){
                initPool();
            }
            jedis = jedisPool.getResource();
            local.set(jedis);
        }
        return jedis;
    }
    //关闭连接
    public static void closeConn(){
        Jedis jedis = local.get();
        if(jedis != null){
            jedis.close();
        }
        local.set(null);
    }
    //关闭线程池
    public static void closePool(){
        if(jedisPool != null){
            jedisPool.close();
        }
    }
}
