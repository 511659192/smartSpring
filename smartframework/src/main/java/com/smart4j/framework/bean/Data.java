package com.smart4j.framework.bean;

import lombok.Getter;

/**
 * Created by ym on 2018/5/11.
 */
@Getter
public class Data {

    private Object model;

    public Data() {
    }

    public Data(Object model) {
        this.model = model;
    }
}
