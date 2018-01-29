package springBootPassage1.example2;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Example2Test {
    AnnotationConfigApplicationContext ctx = null;
    @Test
    public void test1(){
        ctx = new AnnotationConfigApplicationContext(JavaConfig.class);

        UseFunctionService1 useFunctionService = ctx.getBean(UseFunctionService1.class);
        UseFunctionService1 useFunctionService2 = ctx.getBean(UseFunctionService1.class);

        System.out.println(useFunctionService.SayHello("Jake"));

        System.out.println(useFunctionService.hashCode() + ";"+useFunctionService2.hashCode());

        ctx.close();
    }
}
