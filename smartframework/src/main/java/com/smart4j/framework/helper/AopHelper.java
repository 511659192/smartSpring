package com.smart4j.framework.helper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.smart4j.framework.annotation.Aspect;
import com.smart4j.framework.proxy.AbstractProxy;
import com.smart4j.framework.proxy.Proxy;
import com.smart4j.framework.proxy.ProxyManager;
import com.smart4j.framework.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by ym on 2018/5/13.
 */
public class AopHelper {

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = Sets.newHashSet();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !Objects.equals(aspect, annotation)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = Maps.newHashMap();
        addAspectProxyMap(proxyMap);
        addTransactionProxyMap(proxyMap);
        return proxyMap;
    }

    private static void addTransactionProxyMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getServiceClassSet();
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }

    private static void addAspectProxyMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> subClassSet = ClassHelper.getClassSetBySupper(AbstractProxy.class);
        for (Class<?> clazz : subClassSet) {
            if (clazz.isAnnotationPresent(Aspect.class)) {
                Aspect annotation = clazz.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(annotation);
                proxyMap.put(clazz, targetClassSet);
            }
        }
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>, List<Proxy>> targetMap = Maps.newHashMap();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = Lists.newArrayList(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
