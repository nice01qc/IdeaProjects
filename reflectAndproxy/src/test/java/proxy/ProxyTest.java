package proxy;

import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.lang.reflect.*;

public class ProxyTest {
    // 普通测试一
    @Test
    public void test1(){
        // 创建实例
        ProxyInterfaceImpl proxyObject = new ProxyInterfaceImpl();
        proxyObject.setAge(22);

        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invoke beginnig....");
                Object result = method.invoke(proxyObject,args);        // 必须传入需要的代理的对象
                System.out.println("invoke ending....");
                return result;
            }
        };

        // Interfaces 参数必须从目标对象中获取
        ProxyInterface proxyInterface = (ProxyInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),proxyObject.getClass().getInterfaces(),handler);
        proxyInterface.nice();

        Class[] classes = proxyObject.getClass().getInterfaces();
    }

    // 普通测试二，更加详细的步骤
    @Test
    public void test2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ProxyInterfaceImpl proxyObject = new ProxyInterfaceImpl();
        proxyObject.setAge(22);

        // InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
        // 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invoke beginnig....");
                Object result = method.invoke(proxyObject,args);        // 必须传入需要的代理的对象
                System.out.println("invoke ending....");
                return result;
            }
        };
        // 通过 Proxy 为包括 Interface 接口在内的一组接口动态创建代理类的类对象
        Class clazz = Proxy.getProxyClass(Thread.currentThread().getContextClassLoader(),proxyObject.getClass().getInterfaces());

        // 通过反射从生成的类对象获得构造函数对象
        Constructor constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});

        // 通过构造函数对象创建动态代理类实例
        ProxyInterface proxyInterface = (ProxyInterface)constructor.newInstance(new Object[]{handler});

        proxyInterface.nice();

    }

    // ·测试cglib动态代理
    @Test
    public void testCglib(){
        Enhancer enhancer = new Enhancer();
        // 继承被代理类，作为父类
        enhancer.setSuperclass(MyCglibObject.class);
        // 设置回调
        enhancer.setCallback(new MyCglibProxy());
        // 生成代理类对象
        MyCglibObject myCglibObject = (MyCglibObject)enhancer.create();

        myCglibObject.say();

        System.out.println(myCglibObject.getClass().getSimpleName());

    }

}
