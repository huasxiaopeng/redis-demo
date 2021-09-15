package com.example.cachedemo.controller;

import com.example.cachedemo.entity.Country;
import com.example.cachedemo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/9/14
 * @desc
 */
@RestController
@RequestMapping("/con")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @PostMapping("/put")
    public Country saveStudent(@RequestBody Country country){
        return countryService.saveCountry(country);
    }

    @DeleteMapping("/evit/{id}")
    public void deleteStudentById(@PathVariable("id") Integer id){
        countryService.deleteCountryById(id);
    }

    @GetMapping("/able/{id}")
    public Country findStudentById(@PathVariable("id") Integer id){
        return countryService.findCountryById(id);
    }
}
