package com.example.cachedemo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/8/11
 * @desc
 */
@Configuration
public class RedisConfig {
    /**
     *  配置序列化协议
     * @param redisConnectionFactory
     * @return
     */
  @Primary
  @Bean
  public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
      RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(redisConnectionFactory);
      // 使用Jackson2JsonRedisSerialize 替换默认序列化
      Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
      objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//      SimpleModule simpleModule = new SimpleModule();
////      simpleModule.addSerializer(DateTime.class, new JodaDateTimeJsonSerializer());
////      simpleModule.addDeserializer(DateTime.class, new JodaDateTimeJsonDeserializer());
//      objectMapper.registerModule(simpleModule);
      jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
      // 设置value的序列化规则和 key的序列化规则
      redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
      redisTemplate.setKeySerializer(new StringRedisSerializer());

      redisTemplate.setHashKeySerializer(new StringRedisSerializer());
      redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
      redisTemplate.afterPropertiesSet();
      return redisTemplate;
  }
    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        //缓存配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30L)) //设置缓存的默认超时时间：30分钟
                .disableCachingNullValues()             //如果是空值，不缓存
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))         //设置key序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((valueSerializer())));  //设置value序列化器

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}

