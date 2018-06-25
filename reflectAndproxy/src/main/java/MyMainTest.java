import java.lang.reflect.*;

public class MyMainTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MyInterfaceImp myInterfaceImp = new MyInterfaceImp();
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invoke beginnig....");
                Object result = method.invoke(myInterfaceImp,args);        // 必须传入需要的代理的对象
                System.out.println("invoke ending....");
                return result;
            }
        };

        Class<?> clazz = Proxy.getProxyClass(Thread.currentThread().getContextClassLoader(),myInterfaceImp.getClass().getInterfaces());

        Constructor constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});

        MyInterface myInterface = (MyInterface)constructor.newInstance(new Object[]{handler});

        myInterface.sout();



    }
}
