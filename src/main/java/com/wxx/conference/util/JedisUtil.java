package com.wxx.conference.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by thinkpad on 2020-5-28.
 */
public class JedisUtil {

    private JedisPool jedispool;

    public JedisPool getJedispool() {
        return jedispool;
    }
    //通过setx注入poolWriper 获取jedispool
    public void setJedispool(JedisPoolWriper poolWriper) {
        this.jedispool = poolWriper.getJedisPool();
    }
    //获取jedis对象
    public Jedis getJedis() {
        return jedispool.getResource();
    }
    public void closeJedis(){
        jedispool.close();
    }
}
