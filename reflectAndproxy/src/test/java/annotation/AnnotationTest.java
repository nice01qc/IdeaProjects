package annotation;

import org.junit.Test;

import java.lang.reflect.Field;

public class AnnotationTest {
    @Test
    public void testBoy() throws ClassNotFoundException, IllegalAccessException {
        Class<?> boy = Class.forName("annotation.TestBoy");
        Field[] fields = boy.getDeclaredFields();
        TestBoy testBoy = new TestBoy();
        for (int i = 0; i < fields.length; i++){
            String string = fields[i].getDeclaredAnnotations()[0].annotationType().getName();
            int index = string.lastIndexOf(".")+1;
            String annotation = string.substring(index);
            System.out.println(fields[i].getName()+"<->"+annotation);
            if (annotation.equals("Boy")){
                Boy b = (Boy)fields[i].getDeclaredAnnotations()[0];
                System.out.println(b.name()+"  ;  " + b.age());
            }


        }

    }


}
