package other;

import java.lang.reflect.*;

public class DynamicPoroxy implements InvocationHandler {

    private Object object;

    public DynamicPoroxy(Object object){
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before.....");
        Parameter[] parameters = method.getParameters();
        if (parameters.length>0){
            for (Parameter parameter : parameters){

            }
        }

        if(method.getGenericParameterTypes().length>0){
            Type[] types = method.getGenericParameterTypes();
            for(Type type : types){
                System.out.println(type.toString().substring(type.toString().lastIndexOf(".")+1));
            }
        }
        Object result = null;

        result = method.invoke(object,args);

        System.out.println("after......");

        return result;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(
                object.getClass().getClassLoader(),object.getClass().getInterfaces(),this);
    }

    public static void main(String[] args) {
        Hello hello = new HelloImp();

        DynamicPoroxy dynamicPoroxy = new DynamicPoroxy(hello);

        Hello helloProxy = dynamicPoroxy.getProxy();

        helloProxy.say("jake");

    }

}
