package annotationAndReflect;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationTest {
    @Test
    public void testBoy() throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Class<?> testboyclass = Class.forName("annotationAndReflect.TestBoy");
        TestBoy testBoy = new TestBoy(233,"Mike");

        // 输出所有属性
        Field field1 = testboyclass.getDeclaredField("a");  //先输出一个
        System.out.println("field1's value: "+field1.getInt(testBoy)+";\ttype: "+field1.getType()+";\t");
        System.out.println("---------------------------");
        Field[] fields = testboyclass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++){
            Field field = fields[i];
            String name = field.getName();
            Object value = field.get(testBoy);
            System.out.println("varieable name:"+name+"\tvalue:"+value);
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (int j = 0; j<annotations.length; j++){
                Annotation annotation = annotations[j];
                String annotationTypeName = annotation.annotationType().getSimpleName();
                // 仅判断现有的注解类
                if ("Boy".equals(annotationTypeName)){
                    Boy boy = (Boy)annotation;
                    System.out.println("age:\t"+boy.age()+"\tname:"+boy.name());
                }else if ("Girl".equals(annotationTypeName)){
                    Girl girl = (Girl)annotation;
                    System.out.println("age:\t"+girl.age()+"\tname:"+girl.name());
                }
            }
        }

        // 现在判断方法
        System.out.println("---------------------------");
        Method method1 = testboyclass.getDeclaredMethod("getStr",null); // 先搞一个试试
        System.out.println(method1.getName()+"\tname:"+method1.invoke(testBoy));
//        Method[] methods = testboyclass.getMethods(); // 这是获取所有类包括父类在内的所有公共类
        Method[] methods = testboyclass.getDeclaredMethods();   // 获取当前类所有方法，不分私公

        for (int i = 0; i< methods.length; i++){
            Method method = methods[i];
            String name = method.getName();
            int count = method.getParameterCount();
            if (count == 0){
                if ("void".equals(method.getReturnType().getSimpleName())){
                    method.invoke(testBoy);
                    System.out.println("the void method is"+name +"()");
                }else{
                    System.out.println("the execute result of " + name +"()" + ":\t"+ method.invoke(testBoy));
                }
            }else{
                System.out.println("the parametes method is "+name +"(" + count +")");
            }
        }

        /**
         * 其他的都同上
         */

    }


}
