package step1;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {

        //设置为true,会在工程根目录生成$Proxy0.class代理类（com.sun.proxy.$Proxy0.class）
        System.getProperties().put(
                "sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        String saveGeneratedFiles = System.getProperty("sun.misc.ProxyGenerator.saveGeneratedFiles");
        System.out.println(saveGeneratedFiles);


        HelloTest helloWord = new HelloTestImpl();
        CustomInvocationHanlder customInvocationHandler = new CustomInvocationHanlder(helloWord);
        //通过Proxy.newProxyInstance生成代理对象
        HelloTest proxy = (HelloTest) Proxy.newProxyInstance(
                HelloTest.class.getClassLoader(),
                helloWord.getClass().getInterfaces(), customInvocationHandler);
        //调用say方法
        proxy.say("test");


    }
}
