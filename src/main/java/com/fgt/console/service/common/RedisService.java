package com.fgt.console.service.common;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author 执笔
 */
@Service
public interface  RedisService {

    /**
     * 获取jedis实例
     * @return
     */
    Jedis getResource();

    /**
     * jedis写操作
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * jedis读操作
     * @param key
     * @return
     */
    String get(String key);

}
