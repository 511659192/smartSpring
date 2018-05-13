package com.smart4j.framework.proxy;

/**
 * Created by ym on 2018/5/13.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
