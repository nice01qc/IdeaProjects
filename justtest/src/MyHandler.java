import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyHandler implements InvocationHandler {

    MyInterface myInterfaceImp;

    MyHandler(MyInterface myInterface){
        this.myInterfaceImp = myInterface;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke beginnig....");
        Object result = method.invoke(myInterfaceImp,args);        // 必须传入需要的代理的对象
        System.out.println("invoke ending....");
        return result;
    }
}
