package com.hrbeu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.hrbeu.dao")
public class MynewblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MynewblogApplication.class, args);
    }

}
