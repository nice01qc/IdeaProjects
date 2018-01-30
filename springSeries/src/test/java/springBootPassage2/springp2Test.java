package springBootPassage2;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class springp2Test {

    AnnotationConfigApplicationContext context = null;

    @Test
    public void test1(){

        context = new AnnotationConfigApplicationContext(ElConfig.class);

        ElConfig resourceService = context.getBean(ElConfig.class);

        resourceService.outputResource();

        context.close();
    }
}
