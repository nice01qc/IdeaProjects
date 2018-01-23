package test2aop;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();
    public Object getProxy(Class clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        System.out.println("before .....");

        try{
            System.out.println("method before ");
            result = methodProxy.invokeSuper(o,objects);
            System.out.println("method after.....");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("exception......");
        }finally {
            System.out.println("finally .....");
        }

        return result;
    }
}
