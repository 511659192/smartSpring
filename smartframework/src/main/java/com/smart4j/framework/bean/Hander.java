package com.smart4j.framework.bean;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * Created by ym on 2018/5/11.
 */
@Getter
public class Hander {

    private Class<?> controllerClass;

    private Method actionMethod;

    public Hander(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }
}
