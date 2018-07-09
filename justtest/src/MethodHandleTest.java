import java.io.ObjectInputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {

    static class ClassA{
        public void println(String a){
            System.out.println(a);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() %2 == 2 ? System.out : new ClassA();

        getPrintlnMH(obj).invokeExact("icyfenix");

    }

    private static MethodHandle getPrintlnMH(Object receiver) throws Throwable{
        MethodType mt = MethodType.methodType(void.class,String.class);
        return MethodHandles.lookup().findVirtual(receiver.getClass(),"println",mt).bindTo(receiver);
    }


}
