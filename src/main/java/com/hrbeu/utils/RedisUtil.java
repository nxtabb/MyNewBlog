package com.hrbeu.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname RedisUtil
 * @Description TODO
 * @Date 2021/3/9 11:24
 * @Created by nxt
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> myRedisTemplate;
    //设置缓存过期时间(expire)
    public boolean expire(String key,long time){
        try {
            if(time>0){
                myRedisTemplate.expire(key,time, TimeUnit.SECONDS);

            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //获取过期时间(ttl)
    public long getExpire(String key){
        long time = 0L;
        try {
            time = myRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        }catch (NullPointerException e){
            System.out.println("该对象不存在");
            return 0;
        }
        return time;
    }

    //key是否存在
    public boolean hasKey(String key){
        try {
            return myRedisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //删除key
    public void del(String... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                myRedisTemplate.delete(key[0]);
            }else {
                myRedisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    //String 类型的获取 get
    public Object get(String key){
        if(key==null){
            return null;
        }
        else {
            ValueOperations<String, Object> forValue = myRedisTemplate.opsForValue();
            return forValue.get(key);
        }

    }

    //String 类型的设置 set
    public boolean set(String key,Object value){
        ValueOperations<String, Object> forValue = myRedisTemplate.opsForValue();
        try{
            forValue.set(key,value);
            return true;
        }catch (Exception e){
            System.out.println("set失败"+e.getMessage());
            return false;
        }
    }

    //String类型放入并设置缓存时间  setex,如果时间小于等于0，则永久存入
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                ValueOperations<String, Object> forValue = myRedisTemplate.opsForValue();
                forValue.set(key,value,time,TimeUnit.SECONDS);
            }else {
                set(key,value);
            }
        }catch (Exception e){
            System.out.println("存入失败:"+e.getMessage());
            return false;
        }
        return true;
    }

    //增加String类型的 value的值   incr key value
    public long incr(String key,long delta){
        if(delta>0){
            ValueOperations<String, Object> forValue = myRedisTemplate.opsForValue();
            forValue.increment(key,delta);
            return delta;
        }else {
            throw new RuntimeException("增加的值必须大于0，因此增加失败");
        }
    }

    //减少String 类型的value的值， decr key value
    public long decr(String key,long delta){
        if(delta>0){
            ValueOperations<String, Object> forValue = myRedisTemplate.opsForValue();
            forValue.decrement(key,delta);
            return delta;
        }else {
            throw new RuntimeException("减少的值必须大于0，因此减少失败");
        }
    }

    //hash类型的get hget key hkey
    public Object hget(String key,String hashKey){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        return forHash.get(key, hashKey);
    }

    //hash类型的hmget key 所有的hkey
    public Map<Object,Object> hmget(String key){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        return forHash.entries(key);

    }

    //hash 类型的set
    public boolean hmset(String key,Map<Object,Object> map){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        try {
            forHash.putAll(key,map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    //hash类型的hmset，并设置过期时间
    public boolean hmset(String key,Map<Object,Object> map,long time){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        try {
            forHash.putAll(key,map);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //hash类型的set hset key hkey hvalue
    public boolean hset(String key,String hkey,Object hvalue){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        try {
            forHash.put(key,hkey,hvalue);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    //hash hset 并设置过期时间
    public boolean hset(String key,String hkey,Object hvalue,long time){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        try {
            forHash.put(key,hkey,hvalue);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    //hash hdel hdel key hkey1 hkey2....
    public void hdel(String key,String...hkeys){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        forHash.delete(key,hkeys);
    }

    //hash hhaskey hashkey key hkey
    public boolean hHasKey(String key,String hkey){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        return forHash.hasKey(key, hkey);
    }

    //hash hincrby key hkey incr
    public void hincrby(String key,String hkey,int incr){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        forHash.increment(key,hkey,incr);
        System.out.println(key+"下的"+hkey+"增加了"+incr);
    }

    //hash hdecrby key hkey decr
    public void hdecrby(String key,String hkey,int decr){
        HashOperations<String, Object, Object> forHash = myRedisTemplate.opsForHash();
        forHash.increment(key,hkey,-decr);
        System.out.println(key+"下的"+hkey+"减少了"+(-decr));
    }

    //set sadd key value1 value2....
    public long sadd(String key,Object...values){
        SetOperations<String, Object> forSet = myRedisTemplate.opsForSet();
        try {
            return forSet.add(key, values);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("set添加失败");
            return 0;
        }
    }

    //set saddex 向set中添加元素并设置有效时间
    public long saddex(String key,long time,Object...values){
        SetOperations<String, Object> forSet = myRedisTemplate.opsForSet();
        try {
            Long addNum = forSet.add(key, values);
            if(time>0){
                expire(key,time);
            }
            return addNum;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("添加并设置时间失败");
            return 0;
        }
    }

    //set scard scard key  获取set的大小
    public long scard(String key){
        try {
            SetOperations<String, Object> forSet = myRedisTemplate.opsForSet();
            return forSet.size(key);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("获取set的大小失败");
            return -1;
        }


    }

    //set srem key value 从set中移除一个元素
    public long srem(String key,Object...values){
        SetOperations<String, Object> forSet = myRedisTemplate.opsForSet();
        try {
            return forSet.remove(key,values);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("移除元素失败");
            return 0;
        }

    }

    //list llen key 查询key下的list的长度
    public long llen(String key){
        ListOperations<String, Object> forList = myRedisTemplate.opsForList();
        try {
            return forList.size(key);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("查询失败");
            return 0;
        }

    }

    //list lindex key n 返回从左起 下标为n的元素
    public Object lindex(String key,int index){
        ListOperations<String, Object> forList = myRedisTemplate.opsForList();
        try {
            return forList.index(key,index);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("不能获取到该值");
            return null;
        }
    }

    //list lpush key value1 value2 value3
    public boolean lpush(String key,Object...values){
        ListOperations<String, Object> forList = myRedisTemplate.opsForList();
        try {
            //返回当前
            forList.leftPushAll(key, values);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("push失败");
            return false;
        }
    }

    //list lpush key value1 value2...并且设置生效时间
    public boolean lpushex(String key,long time,Object...values){
        ListOperations<String, Object> forList = myRedisTemplate.opsForList();
        try {
            forList.leftPushAll(key,values);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("插入并设置时间失败");
            return false;
        }
    }

    //list lset key index value
    public boolean lset(String key,long index,Object value){
        ListOperations<String, Object> forList = myRedisTemplate.opsForList();
        try {
            forList.set(key,index,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //list lrem key n value 返回移除了多少个这样的值
    public long lrem(String key,long n,Object value){
        ListOperations<String, Object> forList = myRedisTemplate.opsForList();
        try {
            if(n>0){
                return forList.remove(key, n, value);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("移除失败，请确认后再删除");
            return -1;
        }
        return 0;
    }
}
