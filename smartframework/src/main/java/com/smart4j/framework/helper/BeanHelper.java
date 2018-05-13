package com.smart4j.framework.helper;

import com.google.common.collect.Maps;
import com.smart4j.framework.util.ReflectionUtil;

import java.util.Map;
import java.util.Set;

/**
 * Created by ym on 2018/5/11.
 */
public class BeanHelper {

    public final static Map<Class<?>, Object> BEAN_MAP = Maps.newHashMap();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz : beanClassSet) {
            Object instance = ReflectionUtil.getInstance(clazz);
            BEAN_MAP.put(clazz, instance);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> clazz) {
        Object instance = BEAN_MAP.get(clazz);
        if (instance == null) {
            throw new RuntimeException("bean of " + clazz.getName() + " can not be found");
        }
        return (T) instance;
    }

    public static void setBean(Class<?> clazz, Object object) {
        BEAN_MAP.put(clazz, object);
    }

}
