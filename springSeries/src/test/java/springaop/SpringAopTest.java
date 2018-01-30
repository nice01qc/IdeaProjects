package springaop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAopTest {
    ApplicationContext context = null;

    @Test
    public void test1(){
        context = new ClassPathXmlApplicationContext("springaop.xml");

        // 如果是jdk动态代理，需用接口，如果是Cglib代理，则两者都可以
        NaiveWaiter naiveWaiter = context.getBean(NaiveWaiter.class);

        naiveWaiter.greetTo("jake");

    }
}
