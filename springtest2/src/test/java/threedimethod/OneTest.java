package threedimethod;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OneTest {



    @Test
    public void test1(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("one.xml");

        One one = (One)ac.getBean("one");
        Boy boy = (Boy)ac.getBean("boy");


        System.out.println(one);
        System.out.println(boy.name);
    }

    @Test
    public void test2(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("oneTestAnnotaion.xml");

        One one = (One)ac.getBean("one");
        Boy boy = (Boy)ac.getBean("boy");


        System.out.println(one);
        System.out.println(boy.name);
    }


}
