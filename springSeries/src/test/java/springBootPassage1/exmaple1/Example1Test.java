package springBootPassage1.exmaple1;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Example1Test {

    AnnotationConfigApplicationContext ctx = null;
    @Test
    public void test1(){
        ctx = new AnnotationConfigApplicationContext(DiConfig.class);

        UseFunctionService useFunctionService = ctx.getBean(UseFunctionService.class);

        System.out.println(useFunctionService.SayHello("Jake"));

        ctx.close();
    }
}
