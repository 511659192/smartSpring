package com.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by ym on 2018/5/13.
 */
public class ProxyManager {

    public static <T> T createProxy(final Class<T> clazz, final List<Proxy> proxyList) {
        return (T) Enhancer.create(clazz, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                ProxyChain proxyChain = new ProxyChain(obj, clazz, method, args, methodProxy, proxyList);
                return proxyChain.doProxyChain();
            }
        });
    }
}
