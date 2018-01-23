package org.smart4j.chapter4.aspect;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * 测试用例
 */
@Aspect(Aspect.class)
public class ControllerAspect extends AspectProxy{
    @Override
    public void begin() {
        System.out.println("begin()....");
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        System.out.println("after....");
    }
}
