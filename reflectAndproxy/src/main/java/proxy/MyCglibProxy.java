package proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// cglib动态代理
public class MyCglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("begin....\t" + o.getClass().getSimpleName() +";\t" + method.getName() + ";\t" +objects.length );

        Object result = methodProxy.invokeSuper(o,objects);

        System.out.println("ending ...."+"\tresult: " + result);

        return result;
    }

}
