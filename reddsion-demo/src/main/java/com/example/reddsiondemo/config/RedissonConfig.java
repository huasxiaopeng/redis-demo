package com.example.reddsiondemo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/9/15
 * @desc
 */
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonTemplate() throws IOException {
        RedissonClient redisson = Redisson.create(Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream()));
        return redisson;
    }

}
