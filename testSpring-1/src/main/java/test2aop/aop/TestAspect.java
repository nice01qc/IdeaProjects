package test2aop.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspect {
    public static void main(String[] args) {

       ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:aspectj.xml");

       Waiter waiter = (Waiter) ctx.getBean("naiveWaiter");
        Seller seller = (Seller)waiter;
        seller.sell("haoge...");
        System.out.println("------------------------------------");
        Waiter waiter1 = (Waiter)ctx.getBean("naughtyWaiter");
        waiter1.div(3.3,0);

//        waiter1.greetTo("dkalfkdjkl");






    }
}
