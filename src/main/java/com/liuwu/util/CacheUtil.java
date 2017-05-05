package com.liuwu.util;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;
import redis.clients.util.Sharded;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


/**
 * Created by liuwu
 * Create Date 04/15/16.
 * Version 1.0
 */
public class CacheUtil {
    private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);
    private static Pool<ShardedJedis> jedisPool;
    private static volatile CacheUtil instance;
    private static Object object = new Object();

    public static CacheUtil getInstance() {
        if (instance != null) {
            return instance;
        } else {
            synchronized (object) {
                if (instance == null) {
                    instance = new CacheUtil();
                }
            }
            return instance;
        }
    }

    public CacheUtil() {
        initPool();
    }

    private void initPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxWaitMillis(1000);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(100);
        poolConfig.setMaxTotal(2000);
        poolConfig.setBlockWhenExhausted(false);
        try {
            //String configPath = System.getProperty("yt.env", "dev") + "/redis.properties";
            String configPath = "redis.properties";
            logger.info("load redis config, path:{}", configPath);
            PropertiesConfiguration configuration = new PropertiesConfiguration();
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(configPath);
            configuration.load(in);
            List<JedisShardInfo> shards = new ArrayList();
            //String name = configuration.getString("name");
            String cluster = configuration.getString("cluster");
            String[] hosts = cluster.split(";");
            for (String s : hosts) {
                String[] str = s.split(":");
                String host = str[0];
                String port = str[1];
                JedisShardInfo jedis = new JedisShardInfo(host, Integer.valueOf(port));
                shards.add(jedis);
            }
            jedisPool = new ShardedJedisPool(poolConfig, shards, Hashing.MURMUR_HASH,
                    Sharded.DEFAULT_KEY_TAG_PATTERN);
        } catch (Exception ex) {
            logger.error("init redis error", ex);
        }
    }

    public String get(String key) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error("get value from redis error|key={}|ex={}", key, ErrorWriterUtil.WriteError(e).toString());
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
            return result;
        }
    }

    public void set(String key, String value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("set value to redis error|key={}|value={}|ex={}", key, value, StringUtil.stackTrace(e));
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    public void set(String key, String value, Integer expiry) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            if (CheckUtil.isEmpty(expiry) || expiry.equals(-1)) {
                jedis.set(key, value);
            } else {
                jedis.setex(key, expiry, value);
            }
        } catch (Exception e) {
            logger.error("set value to redis error|key={}|value={}|expiry={}|ex={}", key, value, expiry, ErrorWriterUtil.WriteError(e).toString());
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    //********************************************** 对象操作 start **************************************************************

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(byte[] key, byte[] value, Integer expiry) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (CheckUtil.isEmpty(expiry) || expiry.equals(-1)) {
                jedis.set(key, value);
            } else {
                jedis.setex(key, expiry, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("set value to redis error key={}|value={}|expiry={}|ex={}", key, value, expiry, ErrorWriterUtil.WriteError(e));
            return false;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    public byte[] get(byte[] key) {
        ShardedJedis jedis = null;
        byte[] result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error("get redis error key={}|ex={}", key, ErrorWriterUtil.WriteError(e));
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
            return result;
        }
    }

    //    public boolean del(byte[] key){
//        ShardedJedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            jedis.del(key);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }finally{
//            jedisPool.returnResource(jedis);
//        }
//    }
    public long del(byte[] key) {
        return onRedisSync(jedis -> jedis.del(key));
    }


    public boolean sadd(byte[] key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, member);
            return true;
        } catch (Exception e) {
            logger.error("sadd value to redis error key={}|member={}|ex={}", key, member, ErrorWriterUtil.WriteError(e));
            return false;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    public Set<byte[]> getsadd(byte[] key) {
        ShardedJedis jedis = null;
        Set<byte[]> result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.smembers(key);
        } catch (Exception e) {
            logger.error("getsadd redis error key={}|ex={}", key, ErrorWriterUtil.WriteError(e));
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
            return result;
        }
    }

    public boolean sadd(String key, String member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, member);
            return true;
        } catch (Exception e) {
            logger.error("sadd redis error key={}|member={}|ex={}", key, member, ErrorWriterUtil.WriteError(e));
            return false;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    public Set<String> getsadd(String key) {
        ShardedJedis jedis = null;
        Set<String> result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.smembers(key);
        } catch (Exception e) {
            logger.error("getsadd redis error key={}|ex={}", key, ErrorWriterUtil.WriteError(e));
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
            return result;
        }
    }

    /**
     * 从set中删除value
     *
     * @param key
     * @return
     */
    public boolean removeSetValue(String key, String value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.srem(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("removeSetValue redis error ex={}", ex);
            return false;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    public boolean hset(String key, String field, String value) {
        ShardedJedis jedis = null;
        boolean result = false;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
            result = true;
        } catch (Exception e) {
            logger.error("hset redis error key={}|field={}|value={}|ex={}", key, field, value, ErrorWriterUtil.WriteError(e));
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
            return result;
        }
    }

    public String hget(String key, String field) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("hget redis error key={}|field={}|ex={}", key, field, ErrorWriterUtil.WriteError(e));
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
            return result;
        }
    }

    public Map<String, String> hgetAll(String key) {
        ShardedJedis jedis = null;
        Map<String, String> result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("hgetAll redis error key={}|ex={}", key, ErrorWriterUtil.WriteError(e));
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
            return result;
        }
    }

    public boolean hdel(String key, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(key, field);
            return true;
        } catch (Exception e) {
            logger.error("hdel redis error key={}|field={}|ex={}", key, field, ErrorWriterUtil.WriteError(e));
            return false;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    /**
     * 批量删除以某字符串前缀的key(hset存的数据)
     *
     * @param cacheName
     * @param pre_str
     */
    public static void batchHsetDel(String cacheName, String pre_str) {
        Map<String, String> map = CacheUtil.getInstance().hgetAll(cacheName);
        for (String key : map.keySet()) {
            if (key.contains(pre_str)) {
                CacheUtil.getInstance().hdel(cacheName, key);
            }
        }
    }

    //********************************************** 对象操作 end **************************************************************

    public long del(String key) {
        return onRedisSync(jedis -> jedis.del(key));
    }

    public long expire(String key, int seconds) {
        return onRedisSync(jedis -> jedis.expire(key, seconds));
    }

    public long incr(String key) {
        return onRedisSync(jedis -> jedis.incr(key));
    }

    public long incrBy(String key, int value) {
        return onRedisSync(jedis -> jedis.incrBy(key, value));
    }

    public <T> T onRedisSync(Function<ShardedJedis, T> handler) {
        ShardedJedis jedis = null;
        T result = null;
        try {
            jedis = jedisPool.getResource();
            result = handler.apply(jedis);
        } catch (Exception e) {
            logger.error("handler redis sync error", e);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            } else {
                jedisPool.returnBrokenResource(jedis);
            }
            return result;
        }
    }
}
