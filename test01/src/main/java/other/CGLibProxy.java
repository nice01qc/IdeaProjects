package other;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy implements MethodInterceptor {

    private static CGLibProxy instance = new CGLibProxy();

    private CGLibProxy(){}

    public static CGLibProxy getInstance(){
        return instance;
    }



    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        System.out.println("before....");
        result = methodProxy.invokeSuper(obj, objects);
        System.out.println("after.....");
        return result;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    public static void main(String[] args) {
        HelloImp helloProxy = CGLibProxy.getInstance().getProxy(HelloImp.class);
        helloProxy.say("jake");
        helloProxy.say1();
    }



}
