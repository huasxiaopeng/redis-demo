package com.example.cachedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cachedemo.entity.Country;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/9/14
 * @desc
 */
@Mapper
public interface CountryMapper extends BaseMapper<Country> {
}
