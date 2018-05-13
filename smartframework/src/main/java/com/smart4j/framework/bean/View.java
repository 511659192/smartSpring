package com.smart4j.framework.bean;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * Created by ym on 2018/5/11.
 */
@Getter
public class View {

    private String path;

    private Map<String, Object> model;

    public View(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public View(String path) {
        this.path = path;
        this.model = Maps.newHashMap();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }
}
