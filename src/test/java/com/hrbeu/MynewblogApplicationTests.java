package com.hrbeu;

import com.hrbeu.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootTest
class MynewblogApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    //测试redis
    @Test
    public void test01(){
        System.out.println(redisUtil.get("test"));
    }
}
