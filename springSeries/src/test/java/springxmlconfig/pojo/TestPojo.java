package springxmlconfig.pojo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPojo {

    @Test
    public void testBoy(){
        ApplicationContext ctx =new ClassPathXmlApplicationContext("springxmlconfig.xml");

        Boy boy = (Boy)ctx.getBean("boy1");

        boy.boysay();
    }
}
