package com.nicksteger.scheduler.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeanConfig {

    @Bean
    StrongPasswordEncryptor strongPasswordEncryptor () {
        return new StrongPasswordEncryptor();
    }
}
