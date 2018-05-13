package com.smart4j.framework.proxy;

import com.google.common.collect.Lists;
import com.smart4j.framework.bean.Param;
import lombok.Getter;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by ym on 2018/5/13.
 */
@Getter
public class ProxyChain {

    private Object target;
    private Class<?> targetClass;
    private Method targetMethod;
    private Object[] methodParams;
    private MethodProxy methodProxy;
    private int proxyIndex = 0;

    private List<Proxy> proxyList = Lists.newArrayList();

    public ProxyChain(Object target, Class<?> targetClass, Method targetMethod, Object[] methodParams, MethodProxy methodProxy, List<Proxy> proxyList) {
        this.target = target;
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.methodParams = methodParams;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }

    public Object doProxyChain() throws Throwable {
        if (proxyIndex < proxyList.size()) {
            return proxyList.get(proxyIndex++).doProxy(this);
        } else {
            return methodProxy.invokeSuper(target, methodParams);
        }
    }
}
