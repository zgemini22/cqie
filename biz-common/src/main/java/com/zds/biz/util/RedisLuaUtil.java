package com.zds.biz.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Slf4j
@Component
public class RedisLuaUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private static String luaPath = "lua";

    private static String luaAdd = "add.lua";

    private static String luaUpdateStock = "update.lua";

    private static String luaUnlockStock = "unlock.lua";

    private static String luaLockStock = "lock.lua";


    private String getFilePath(String fileName) {
        return luaPath + "/" + fileName;
    }

    /**
     * 调用lua脚本
     * @param key 键
     * @param value 值
     * @param argsone 有效期，秒
     * @param path 脚本地址
     * @return boolean
     */
    private boolean ScrLuaScript(String key, String value, String argsone, String path){
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        keyList.add(value);
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        redisScript.setResultType(String.class);
        String result = "";
        try {
            result = stringRedisTemplate.execute(redisScript, keyList, argsone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "true".equals(result);
    }

    /**
     * 累加指定key的value
     * @param key 键
     * @param upValue 值的变动量
     * @return boolean
     */
    public boolean accumulationValue(String key, String upValue) {
        return ScrLuaScript(key, upValue,"", getFilePath(luaUpdateStock));
    }

    /**
     * 新增键值对
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public boolean addKeyAndValue(String key, String value) {
        return addKeyAndValue(key, value, "");
    }

    /**
     * 新增键值对和有效期
     * @param key 键
     * @param value 值
     * @param time 有效期
     * @return boolean
     */
    public boolean addKeyAndValue(String key,String value,String time){
        return ScrLuaScript(key, value, time, getFilePath(luaAdd));
    }

    /**
     * 删除键值对
     */
    public boolean deleteKey(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 根据key获取value
     */
    public Object getByKey(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 模糊查询出商品keys
     */
    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys("*" + pattern + "*");
    }

    /**
     * 加锁
     *
     * @param key        redis键值对 的 key
     * @param value      redis键值对 的 value  随机串作为值
     * @param timeout    redis键值对 的 过期时间   pexpire 以毫秒为单位
     * @param retryTimes 重试次数   即加锁失败之后的重试次数，根据业务设置大小
     * @return
     */
    public boolean lock(String key, String value, String timeout, int retryTimes) {
        try {
            log.debug("加锁信息：lock :::: redisKey = " + key + " requestid = " + value);
            //执行脚本
            boolean result = ScrLuaScript(key, value, timeout, getFilePath(luaLockStock));
            //存储本地变量
            if (result) {
                log.info("成功加锁：success to acquire lock:" + Thread.currentThread().getName() + ", Status code reply:" + result);
                return true;
            } else if (retryTimes == 0) {
                //重试次数为0直接返回失败
                return false;
            } else {
                //重试获取锁
                log.info("重试加锁：retry to acquire lock:" + Thread.currentThread().getName() + ", Status code reply:" + result);
                int count = 0;
                while (true) {
                    try {
                        //休眠一定时间后再获取锁，这里时间可以通过外部设置
                        Thread.sleep(100);
                        result = ScrLuaScript(key, value, timeout, getFilePath(luaLockStock));
                        if (result) {
                            log.info("成功加锁：success to acquire lock:" + Thread.currentThread().getName() + ", Status code reply:" + result);
                            return true;
                        } else {
                            count++;
                            if (retryTimes == count) {
                                log.info("加锁失败：fail to acquire lock for " + Thread.currentThread().getName() + ", Status code reply:" + result);
                                return false;
                            } else {
                                log.warn(count + " times try to acquire lock for " + Thread.currentThread().getName() + ", Status code reply:" + result);
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        log.error("加锁异常：acquire redis occured an exception:" + Thread.currentThread().getName(), e);
                        break;
                    }
                }
            }
        } catch (Exception e1) {
            log.error("加锁异常：acquire redis occured an exception:" + Thread.currentThread().getName(), e1);
        }
        return false;
    }


    /**
     * 释放KEY
     *
     * @param key   释放本请求对应的锁的key
     * @param value 释放本请求对应的锁的value  是不重复随即串 用于比较，以免释放别的线程的锁
     * @return
     */
    public boolean unlock(String key, String value) {
        try {
            log.info("解锁信息：unlock :::: redisKey = " + key + " requestid = " + value);
            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            boolean result = ScrLuaScript(key, value, null, getFilePath(luaUnlockStock));

            //如果这里抛异常，后续锁无法释放
            if (result) {
                log.info("解锁成功：release lock success:" + Thread.currentThread().getName() + ", Status code reply=" + result);
                return true;
            } else {
                //注意：特殊处理，执行lua脚本del释放锁返回null时，执行del
                String redisValue = stringRedisTemplate.opsForValue().get(key);
                if (StringUtils.isBlank(redisValue)) {
                    log.info("解锁成功：release lock success:" + Thread.currentThread().getName() + ", Status code reply=" + result);
                    log.info("解锁成功：release lock success:" + Thread.currentThread().getName() + "执行lua脚本del释放锁返回null时，自动过期释放");
                    return true;
                }
                if (value.equals(redisValue)) {
                    stringRedisTemplate.delete(key);
                    log.info("解锁成功：release lock success:" + Thread.currentThread().getName() + "执行lua脚本del释放锁返回null时，未过期释放执行del");
                    return true;
                }
                log.error("解锁失败：release lock failed:" + Thread.currentThread().getName() + ", del key failed. Status code reply=" + result);
            }
        } catch (Exception e) {
            log.error("解锁异常：release lock occured an exception", e);
        }
        return false;
    }


    /**
     * 修改key的value值
     */
    public void updateKey(String key, String value, long time) {
        if (time == 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        }

    }
}
