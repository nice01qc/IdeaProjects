package test2aop.aop.method;


import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 前置增强接口 MethodBeforeAdvice
 */
public class GreetingBeforeAdvice implements MethodBeforeAdvice {

    public void before(Method method, Object[] objects, Object o) throws Throwable {
        String clientName = (String)objects[0];
        System.out.println("how are you ! Mr. " + clientName + ";"+ method.getName());
    }

}
