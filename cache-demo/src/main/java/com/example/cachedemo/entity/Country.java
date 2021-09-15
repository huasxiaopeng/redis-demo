package com.example.cachedemo.entity;





import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/9/14
 * @desc
 */
public class Country implements Serializable {
   @TableId(type = IdType.AUTO)
    private Integer id;

    private String country;

    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
