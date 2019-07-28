package step1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomInvocationHanlder implements InvocationHandler {

    private Object object;

    public CustomInvocationHanlder(Object object) {
        this.object = object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("Before invocation...");

        Object returnValue = method.invoke(object,args);

        System.out.println("After invocation....");

        return returnValue;
    }
}
