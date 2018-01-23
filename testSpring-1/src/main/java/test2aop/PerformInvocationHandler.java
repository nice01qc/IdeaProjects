package test2aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PerformInvocationHandler implements InvocationHandler {
    private Object object;

    public PerformInvocationHandler(Object object) {
        this.object = object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("before ....");

        try{
            System.out.println("method before.....");
            result = method.invoke(object,args);
            System.out.println("method after.....");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception .....");
        }finally {
            System.out.println("finally .....");
        }


        return result;



    }
}
