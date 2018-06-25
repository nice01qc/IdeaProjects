import java.lang.reflect.*;

public class MyMainTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
         final MyInterfaceImp myInterfaceImp = new MyInterfaceImp();

        InvocationHandler handler = new MyHandler(myInterfaceImp);

        Class<?> clazz = Proxy.getProxyClass(Thread.currentThread().getContextClassLoader(),myInterfaceImp.getClass().getInterfaces());

        Constructor constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});

        MyInterface myInterface = (MyInterface)constructor.newInstance(new Object[]{handler});

        myInterface.sout();

        System.out.println(clazz.toString());




    }
}
