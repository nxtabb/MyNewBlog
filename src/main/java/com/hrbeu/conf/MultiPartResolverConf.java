package com.hrbeu.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @Classname MultiPartResolverConf
 * @Description TODO
 * @Date 2021/4/14 10:51
 * @Created by nxt
 */
@Configuration
public class MultiPartResolverConf {
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxInMemorySize(536870912);
        //上传文件大小 512M 512*1024*1024
        resolver.setMaxUploadSize(512*1024*1024);
        return resolver;
    }
}
