package com.example.cachedemo.service;

import com.example.cachedemo.entity.Country;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/9/14
 * @desc
 */
public interface CountryService {

    public Country saveCountry(Country country);

    public void deleteCountryById(Integer id);

    public Country findCountryById(Integer id);

}
