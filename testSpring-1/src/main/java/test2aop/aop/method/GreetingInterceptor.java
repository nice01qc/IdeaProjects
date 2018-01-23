package test2aop.aop.method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class GreetingInterceptor implements MethodInterceptor {


    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;
        String clientName = (String) methodInvocation.getArguments()[0];
        System.out.println("how are you 2! Mr. "+clientName);

        result = methodInvocation.proceed();

        System.out.println("Please enjoy youself ! 2");
        return result;
    }
}
