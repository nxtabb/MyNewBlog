package com.hrbeu.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Classname RedisConf
 * @Description TODO
 * @Date 2021/3/9 10:40
 * @Created by nxt
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60*30)
public class RedisConf {
    @Bean
//	如果没自己定义redisTemplate，则会生效。
    public RedisTemplate<String, Object> myRedisTemplate(RedisConnectionFactory factory) {
        //自定义的template 范型转化为string，object
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //连接工厂（固定语句）
        template.setConnectionFactory(factory);
        //配置序列化，把任意对象转化为json序列化
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        //配置jackson
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //设置序列化的mapper
        serializer.setObjectMapper(mapper);
        //创建template关于String的序列化
        StringRedisSerializer serializerStr = new StringRedisSerializer();
        //设置template的序列化采用哪种方式
        template.setKeySerializer(serializerStr);
        template.setHashKeySerializer(serializerStr);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        //做个收尾，afterProperties方法是设置默认的序列化，除了上面被设置的序列化方式，其他的地方还是采用默认的序列化方式
        template.afterPropertiesSet();
        return template;
    }




}
