package springxmlconfig.pojo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPojo {
    ApplicationContext ctx = null;
    @Test
    public void TestPojo(){
        ctx = new ClassPathXmlApplicationContext("springxmlconfig.xml");    // 获取bean

        Boy boy = (Boy)ctx.getBean("boy1");
//        Boy boy2 = ctx.getBean(Boy.class);
//        boy2.boysay();
//        System.out.println(boy.hashCode()+";" + boy2.hashCode());

        Girl girl1 = (Girl) ctx.getBean("girl1");
        Girl girl2 = (Girl) ctx.getBean("girl1");

        girl1.girlSay();
        System.out.println(girl1.hashCode()+";"+girl2.hashCode());

        Boy boy22 = (Boy)ctx.getBean("boy2");
        boy22.boysay();

        Girl girl3 = (Girl) ctx.getBean("girl2");
        girl3.girlSay();
    }

    @Test
    public void TestPojo1(){
        ctx = new ClassPathXmlApplicationContext("springannotationconfig.xml");    // 获取bean

        Boy boy = (Boy)ctx.getBean("boy1");
//        Boy boy2 = ctx.getBean(Boy.class);
//        boy2.boysay();
//        System.out.println(boy.hashCode()+";" + boy2.hashCode());

        Girl girl1 = (Girl) ctx.getBean("girl1");
        Girl girl2 = (Girl) ctx.getBean("girl1");

        girl1.girlSay();
        System.out.println(girl1.hashCode()+";"+girl2.hashCode());

        Boy boy22 = (Boy)ctx.getBean("boy2");
        boy22.boysay();

        Girl girl3 = (Girl) ctx.getBean("girl2");
        girl3.girlSay();

    }

}
