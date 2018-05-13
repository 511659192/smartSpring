package com.smart4j.framework.util;

import com.smart4j.framework.bean.Data;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ym on 2018/5/12.
 */
public class CGLibDynamicProxy {

    private static CGLibDynamicProxy proxy = new CGLibDynamicProxy();

    public static CGLibDynamicProxy getInstance() {
        return proxy;
    }

    public <T> T getProxy(T bean) {
        return (T) Enhancer.create(bean.getClass(), new CGLibInterceptor(bean));
    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, CGLibDynamicProxy.class.getResource("/").getPath());

        Data data = new Data(11);
        data = CGLibDynamicProxy.getInstance().getProxy(data);
        System.out.println(data.getModel());
    }
}
