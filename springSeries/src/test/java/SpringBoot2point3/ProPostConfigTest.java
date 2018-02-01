package SpringBoot2point3;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProPostConfigTest {

    AnnotationConfigApplicationContext context = null;


    @Test
    public void test1(){
        context = new AnnotationConfigApplicationContext(PrePostConfig.class);

        BeanWayService beanWayService = context.getBean(BeanWayService.class);

        JSP250WayService jsp250WayService = context.getBean(JSP250WayService.class);

//        PrePostConfig prePostConfig = context.getBean(PrePostConfig.class);

        context.close();
    }
}
