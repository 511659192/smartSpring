package com.smart4j.framework.proxy;

import java.lang.reflect.Method;

/**
 * Created by ym on 2018/5/13.
 */
public abstract class AbstractProxy implements Proxy {

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Class<?> targetClass = proxyChain.getTargetClass();
        Object[] methodParams = proxyChain.getMethodParams();
        Method targetMethod = proxyChain.getTargetMethod();
        begin();
        try {
            if (accept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } finally {
            end();
        }
        return result;
    }

    protected void end() {

    }

    protected void after(Class<?> targetClass, Method targetMethod, Object[] methodParams, Object result) {

    }

    protected void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {

    }

    protected boolean accept(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        return true;
    };

    protected void begin() {

    }

    ;


}
