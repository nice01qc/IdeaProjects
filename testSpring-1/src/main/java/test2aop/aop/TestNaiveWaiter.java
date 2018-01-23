package test2aop.aop;


import org.aopalliance.intercept.Interceptor;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import test2aop.aop.method.GreetingAfterAdvice;
import test2aop.aop.method.GreetingBeforeAdvice;
import test2aop.aop.method.GreetingInterceptor;

public class TestNaiveWaiter {
    public static void main(String[] args) {
        Waiter waiter = new NaiveWaiter();

        BeforeAdvice advice = new GreetingBeforeAdvice();
        AfterAdvice afterAdvice = new GreetingAfterAdvice();
        //环绕代理
        Interceptor interceptor = new GreetingInterceptor();
        ProxyFactory proxyFactory = new ProxyFactory();

        proxyFactory.setOptimize(true);

        proxyFactory.setTarget(waiter);
//        proxyFactory.addAdvice(advice);
//        proxyFactory.addAdvice(afterAdvice);
        proxyFactory.addAdvice(interceptor);



        Waiter proxyWaiter = (Waiter) proxyFactory.getProxy();
        proxyWaiter.greetTo("haoge");


        System.out.println("----------------------");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:proxyfactory.xml");

        Waiter waiter1 = (Waiter) ctx.getBean("waiter");

        waiter1.greetTo("haoge...");


        System.out.println("------------------------");




    }
}





















