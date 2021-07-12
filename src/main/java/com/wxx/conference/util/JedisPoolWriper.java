package com.wxx.conference.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by thinkpad on 2020-5-28.
 */
public class JedisPoolWriper {
    //创建jedis对象
    JedisPool jedisPool;

    //构造函数注入JedisPoolConfig和地址和端口信息
//    public JedisPoolWriper(final JedisPoolConfig jedisPoolConfig,
//                           final String host, final int port) {
//        try {
//            jedisPool=new JedisPool(jedisPoolConfig,host,port);
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }
    public JedisPoolWriper(final JedisPoolConfig jedisPoolConfig,
                           final String host, final int port,int timeout,String password) {
        try {
            jedisPool=new JedisPool(jedisPoolConfig,host,port,timeout,password);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public JedisPool getJedisPool() {
        return jedisPool;
    }
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

}
