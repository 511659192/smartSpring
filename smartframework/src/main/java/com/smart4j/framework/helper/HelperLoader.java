package com.smart4j.framework.helper;

import com.smart4j.framework.util.ClassUtil;

/**
 * Created by ym on 2018/5/11.
 */
public class HelperLoader {

    public static void init() {
        Class<?>[] classes = new Class[] {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> clazz : classes) {
            ClassUtil.loadClass(clazz.getName(), true);
        }
    }
}
