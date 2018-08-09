package aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {


    @Test
    public void testaop(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("one/aop.xml");

        T testBean = (T)applicationContext.getBean("testBean");


        testBean.test();
    }






}
