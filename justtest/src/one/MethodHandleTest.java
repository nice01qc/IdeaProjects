package one;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {

    static class ClassA {
        public void println(String a){
            System.out.println(a);
        }
    }

    public static void main(String[] args) throws Throwable{

//        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
//
//        getPrintlnMH(obj).invokeExact("icyfenxi");

        Integer a = 1;
        Integer b = 1;
        Long g = 2l;
        System.out.println(g == (a+b));
        System.out.println(g.equals(a+b));
        System.out.println((a == b));

    }



    private static MethodHandle getPrintlnMH(Object reveiver) throws Throwable{
        MethodType mt =MethodType.methodType(void.class,String.class);
        return MethodHandles.lookup().findVirtual(reveiver.getClass(),"println",mt).bindTo(reveiver);
    }

}
