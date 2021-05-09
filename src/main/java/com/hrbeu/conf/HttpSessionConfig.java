//package com.hrbeu.conf;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.session.data.redis.config.ConfigureRedisAction;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
///**
// * @Classname HttpSessionConfig
// * @Description TODO
// * @Date 2021/4/19 21:11
// * @Created by nxt
// */
//@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60*30)//设置session过期时间,默认也是1800秒
//public class HttpSessionConfig {
//    //enableRedisKeyspaceNotificationsInitializer
//    @Bean
//    public static ConfigureRedisAction configureRedisAction() {
//        return ConfigureRedisAction.NO_OP;
//    }
//}
