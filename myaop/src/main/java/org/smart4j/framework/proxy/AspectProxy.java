package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

public class AspectProxy implements Proxy {

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] paras = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, paras)) {
                before(cls, method, paras);
                result = proxyChain.doProxyChain();
                after(cls, method, paras, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            error(cls, method, paras, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }


    public void begin() {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {

    }

    public void end() {

    }

}
