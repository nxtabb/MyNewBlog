package com.hrbeu.conf;

import com.hrbeu.filter.CharacterFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname CharacterFilterConf
 * @Description TODO
 * @Date 2021/4/14 10:42
 * @Created by nxt
 */
@Configuration
public class CharacterFilterConf {
    @Bean
    public FilterRegistrationBean characterFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new CharacterFilter());
        registration.addUrlPatterns("*"); //
        registration.setName("CharacterFilter");
        return registration;
    }
}
