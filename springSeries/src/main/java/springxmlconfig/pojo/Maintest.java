package springxmlconfig.pojo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Maintest {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("springxmlconfig.xml");    // 获取bean

        Boy boy = (Boy)ctx.getBean("boy1");
        Boy boy2 = ctx.getBean(Boy.class);

        Girl girl1 = (Girl) ctx.getBean("girl1");

        boy2.boysay();
        System.out.println(boy.hashCode()+";" + boy2.hashCode());

        
    }
}
