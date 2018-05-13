package com.smart4j.framework.bean;

import lombok.Data;

import java.util.Map;

/**
 * Created by ym on 2018/5/11.
 */
@Data
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public <T> T get(String key) {
        return (T) paramMap.get(key);
    }
}
