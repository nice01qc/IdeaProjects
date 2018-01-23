package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import test1.Haoge;

/**
 * 使用注解定义bean
 */
public class AnnotationApplicationContext3 {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans1.xml");
        Haoge haoge = (Haoge) ctx.getBean("haoge");
        System.out.println(haoge);

        Car1 car1 = (Car1) ctx.getBean("car6");
        System.out.println(car1);
    }
}
