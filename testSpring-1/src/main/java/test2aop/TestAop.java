package test2aop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Proxy;

public class TestAop {
    public static void main(String[] args) {

        Go go = new Go();

        PerformInvocationHandler p = new PerformInvocationHandler(go);


//        G go1 = (G) Proxy.newProxyInstance(go.getClass().getClassLoader(),go.getClass().getInterfaces(),p);
//
//        go1.go();

        CglibProxy cglibProxy = new CglibProxy();

        Go go2 = (Go) cglibProxy.getProxy(go.getClass());

        System.out.println(go2.div(32,0));

    }
}
