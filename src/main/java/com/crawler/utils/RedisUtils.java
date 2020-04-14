package com.crawler.utils;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class RedisUtils {
    private static String ip = "47.111.89.204";
    private static int port = 6380;
    private static int db = 29;
    private static String password = "hqkk_root";

    //私有构造方法
    private RedisUtils() {
    }

    public static ShardedJedis initShardedJedis() {
        ShardedJedis shardedJedis = initialShardedPool();
        int count = 0;
        while (shardedJedis == null) {
            if (count == 10) {
                return null;
            }
            shardedJedis = initialShardedPool();
            count++;
            CommonMethod.sleep(500);
        }
        return shardedJedis;
    }

    /**
     * <p>
     * 方法名称：initialShardedPool
     * </p>
     * <p>
     * 方法描述：redis数据库初始化
     * </p>
     *
     * @return
     * @author Administrator
     * @since 2017年6月19日
     * <p>
     * history 2017年6月19日 Administrator 创建
     * <p>
     */
    public static ShardedJedis initialShardedPool() {
        ShardedJedisPool shardedJedisPool = null;
        ShardedJedis shardedJedis = null;
        try {
            // 池基本配置
            JedisPoolConfig config = initJedisCon();
            List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
            JedisShardInfo jedisinfo = new JedisShardInfo(ip, port, "master");
            jedisinfo.setConnectionTimeout(150000);
            jedisinfo.setSoTimeout(150000);
            if (StringUtils.isNotBlank(password)) {
                jedisinfo.setPassword(password);
            }
            shards.add(jedisinfo);
            // 构造池
            shardedJedisPool = new ShardedJedisPool(config, shards);
            shardedJedis = shardedJedisPool.getResource();
            // 选择数据库
            Collection<Jedis> collection = shardedJedis.getAllShards();
            Iterator<Jedis> jedis = collection.iterator();
            while (jedis.hasNext()) {
                Jedis jedis1 = jedis.next();
                jedis1.select(db);// 选择db
            }
        } catch (Exception e) {
            closeShardedJedis(shardedJedis);
            distoryShardejedispool(shardedJedisPool);
            System.out.println(e);
            return null;
        } finally {
            distoryShardejedispool(shardedJedisPool);
        }
        return shardedJedis;
    }

    public static void closeShardedJedis(ShardedJedis shardedJedis) {
        try {
            shardedJedis.disconnect();
            shardedJedis.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void closeJedis(Jedis jedis) {
        try {
            jedis.disconnect();
            jedis.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void closeJedisPool(JedisPool jedisPool) {
        try {
            jedisPool.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void distoryShardejedispool(ShardedJedisPool shardedJedisPool) {
        try {
            shardedJedisPool.destroy();
            shardedJedisPool.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Jedis initJedis(JedisPool jedisPool) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            return jedis;
        } catch (Exception e) {
            closeJedis(jedis);
        }
        return null;
    }

    public static JedisPool initialJedisPool() {
        // 池基本配置
        JedisPoolConfig config = initJedisCon();
        JedisPool jedisPool = null;
        if (password != null && !"".equals(password)) {
            jedisPool = new JedisPool(config, ip, port, 100000, password);
        } else {
            jedisPool = new JedisPool(config, ip, port);
        }
        return jedisPool;
    }

    public static JedisPoolConfig initJedisCon() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(20); // 最大空闲连接数
        config.setMaxWaitMillis(10000); // 等待可用连接的最大时间
        config.setMaxTotal(500);
        config.setTestOnBorrow(false); // 在空闲时检查有效性,默认false
        return config;
    }
}