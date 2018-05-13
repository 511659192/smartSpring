package com.smart4j.framework.helper;

import com.smart4j.framework.annotation.Inject;
import com.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by ym on 2018/5/11.
 */
public class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap != null && beanMap.size() > 0) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> clazz = entry.getKey();
                Object instance = entry.getValue();
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        Class<?> fieldType = field.getType();
                        Object fieldValue = beanMap.get(fieldType);
                        if (fieldValue != null) {
                            ReflectionUtil.setField(instance, field, fieldValue);
                        }
                    }
                }
            }
        }
    }
}
