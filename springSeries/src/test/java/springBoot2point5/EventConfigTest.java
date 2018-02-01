package springBoot2point5;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EventConfigTest {
    AnnotationConfigApplicationContext context = null;

    @Test
    public void test1(){
        context = new AnnotationConfigApplicationContext(EventConfig.class);

        DemoPublisher demoPublisher = context.getBean(DemoPublisher.class);

        demoPublisher.publish("hello application event ");

        context.close();
    }
}
