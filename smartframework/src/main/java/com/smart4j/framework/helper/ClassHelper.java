package com.smart4j.framework.helper;

import com.google.common.collect.Sets;
import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.annotation.Service;
import com.smart4j.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * Created by ym on 2018/5/11.
 */
public final class ClassHelper {

    private final static Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> controllerClassSet = Sets.newHashSet();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllerClassSet.add(clazz);
            }
        }
        return controllerClassSet;
    }

    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> serviceClassSet = Sets.newHashSet();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                serviceClassSet.add(clazz);
            }
        }
        return serviceClassSet;
    }

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = Sets.newHashSet();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    public static Set<Class<?>> getClassSetBySupper(Class<?> superClass) {
        Set<Class<?>> subClassSet = Sets.newHashSet();
        for (Class<?> clazz : CLASS_SET) {
            if (superClass.isAssignableFrom(clazz) && !Objects.equals(superClass, clazz)) {
                subClassSet.add(clazz);
            }
        }
        return subClassSet;
    }

    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> annotationClassSet = Sets.newHashSet();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                annotationClassSet.add(clazz);
            }
        }
        return annotationClassSet;
    }
}
