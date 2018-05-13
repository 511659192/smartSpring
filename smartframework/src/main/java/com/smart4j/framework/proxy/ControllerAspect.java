package com.smart4j.framework.proxy;

import com.smart4j.framework.annotation.Aspect;
import com.smart4j.framework.annotation.Controller;

import java.lang.reflect.Method;

/**
 * Created by ym on 2018/5/13.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AbstractProxy {

    private Long begin;

    @Override
    protected void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        System.out.println("================before=============");
        System.out.println(String.format("class: %s", targetClass.getName()));
        System.out.println(String.format("method: %s", targetMethod.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    protected void after(Class<?> targetClass, Method targetMethod, Object[] methodParams, Object result) {
        System.out.println(String.format("cast: %s", System.currentTimeMillis() - begin));
        System.out.println("================before=============");
    }
}
