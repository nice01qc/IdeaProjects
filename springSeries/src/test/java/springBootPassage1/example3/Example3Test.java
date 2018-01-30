package springBootPassage1.example3;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example3Test {

    AnnotationConfigApplicationContext context = null;

    @Test
    public void test1(){
        context = new AnnotationConfigApplicationContext(AopConfig.class);
        DemoMthodService demoMthodService = context.getBean(DemoMthodService.class);
        DemoAnnotationService demoAnnotationService = context.getBean(DemoAnnotationService.class);

//        demoMthodService.add();
        demoAnnotationService.add();

    }

}
