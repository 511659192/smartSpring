package com.smart4j.framework.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by ym on 2018/5/12.
 */
public class CGLibInterceptor implements MethodInterceptor {

    private Object target;

    public CGLibInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("11111");
        Object invoke = methodProxy.invokeSuper(target, args);
        System.out.println("2222");
        return invoke;
    }
}
