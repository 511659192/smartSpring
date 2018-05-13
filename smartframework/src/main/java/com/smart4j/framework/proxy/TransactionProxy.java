package com.smart4j.framework.proxy;

import com.smart4j.framework.annotation.Transaction;
import com.smart4j.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * Created by ym on 2018/5/13.
 */
public class TransactionProxy extends AbstractProxy {

    private final static ThreadLocal<Boolean> RUNING_STATE_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Method targetMethod = proxyChain.getTargetMethod();
        Boolean isRunning = RUNING_STATE_HOLDER.get();
        if (!isRunning && targetMethod.isAnnotationPresent(Transaction.class)) {
            RUNING_STATE_HOLDER.set(true);
            Object result;
            try {
                DatabaseHelper.beginTransaction();
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                throw new RuntimeException(e);
            } finally {
                RUNING_STATE_HOLDER.remove();
            }
            return result;
        }
        return proxyChain.doProxyChain();
    }
}
