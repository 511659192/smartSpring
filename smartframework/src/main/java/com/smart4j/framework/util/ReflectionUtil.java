package com.smart4j.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ym on 2018/5/11.
 */
@Slf4j
public class ReflectionUtil {

    public static Object getInstance(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static Object invokeMethod(Object object, Method method, Object... args) {
        method.setAccessible(true);
        Object result;
        try {
            result = method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    public static void setField(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
