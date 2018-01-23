package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationApplicationContext2 {
    public static void main(String[] args) {
        //通过一个带@Configuration的POJO装载Bean配置
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Beans2.class);

        ApplicationContext ctx1 = new ClassPathXmlApplicationContext("beans.xml");

        Car1 car1 = ctx.getBean("car111",Car1.class);
        Car1 car2 = ctx.getBean("car222",Car1.class);
        System.out.println(car1);
        System.out.println(car2);
        Boss boss00 = (Boss) ctx.getBean("boss");
        System.out.println(boss00);


        System.out.println(car1 instanceof Car);



        Car1 car11 = ctx1.getBean("car3",Car1.class);
        System.out.println(car11);

        Car1 car12 = ctx1.getBean("car2",Car1.class);
        System.out.println(car12);


        Car1 car6 = ctx1.getBean("car6",Car1.class);
        System.out.println(car6);

        Secretary secretary = (Secretary) ctx1.getBean("secretary");
        System.out.println(secretary);

        Boss boss = (Boss)ctx1.getBean("boss");
        System.out.println(boss);

        Boss boss1 = (Boss)ctx1.getBean("boss1");
        System.out.println(boss1);

        Me me = (Me) ctx1.getBean("me");
        System.out.println(me);

        Me me1 = (Me) ctx1.getBean("me1");
        System.out.println(me1);

        Me me2 = (Me) ctx1.getBean("me2");
        System.out.println(me2);

        Me me3 = (Me) ctx1.getBean("me3");
        System.out.println(me3);

        Me me4 = (Me) ctx1.getBean("me4");
        System.out.println(me4);

        Me me5 = (Me) ctx1.getBean("me5");
        System.out.println(me5);


    }
}
