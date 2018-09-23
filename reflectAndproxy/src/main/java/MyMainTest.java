import java.lang.reflect.*;

public class MyMainTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles",true);

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

        Class<?>[] c  = myInterfaceImp.getClass().getInterfaces();
        System.out.println(c[0].getSimpleName());
        System.out.println(c[0].isInterface());

        Class<?> clazz = Proxy.getProxyClass(Thread.currentThread().getContextClassLoader(),myInterfaceImp.getClass().getInterfaces());

        Constructor constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});

        MyInterface myInterface = (MyInterface)constructor.newInstance(new Object[]{handler});

        myInterface.sout();
    }
}
