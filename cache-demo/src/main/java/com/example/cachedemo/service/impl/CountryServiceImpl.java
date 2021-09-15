package com.example.cachedemo.service.impl;

import com.example.cachedemo.entity.Country;
import com.example.cachedemo.mapper.CountryMapper;
import com.example.cachedemo.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/9/14
 * @desc
 */
@Service
public class CountryServiceImpl implements CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
    @Autowired
    private CountryMapper countryMapper;

    /**
     *  Q: 现在每个注的过期时间都是相同的。。。。能实现注解配置定义过期时间呢？。。使用配置文件的方式吧，没有注解来的方便
     *   1：脑子刚刚来了个灵感，属于取巧的方式，不依赖spring cache 的管理。既然  @CachePut 会帮我们自动创建key 而且它的key 是通用的时间
     *   所以使用的注解传key 与过期时间，通过redis 直接修改key 的值。
     *
     *   2： 正常方式。因为cachemanger 设置了过期时间，我们需要想办法修改或替换其实现，就这样。
     *
     * @param country
     * @return
     */
    @Override
    @CachePut(value = "country",key = "#country.id")
    // @CachePut 缓存新增的或更新的数据到缓存，其中缓存名称为 country 数据的 key 是 student 的 id
    public Country saveCountry(Country country) {
        int insert = countryMapper.insert(country);
        LOGGER.info("为id、key 为{}的数据做了缓存",country.getId());
        return country;
    }

    @Override
    @CacheEvict(value = "country")
    // @CacheEvict 从缓存 country 中删除 key 为 id 的数据
    public void deleteCountryById(Integer id) {
        LOGGER.info("删除了id、key 为{}的数据缓存", id);
        countryMapper.deleteById(id);
    }

    @Override
    @Cacheable(value = "country",key = "#id" )
//    @Cacheable(value = "country",key = "#id" ,unless="#result == null")
    // @Cacheable 缓存 key 为 id 的数据到缓存 country 中
    public Country findCountryById(Integer id) {
        Country country = null;
        try {
            country = countryMapper.selectById(id);
        } catch (Exception e) {
           LOGGER.error("id:{}查询的数据不存在",id);

        }
        LOGGER.info("为id、key 为{}的数据做了缓存", country);
        return country;
    }
}
