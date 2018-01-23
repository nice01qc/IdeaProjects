package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试引用其他bean
 */
public class TestBeanQuote4 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext pFactory = new ClassPathXmlApplicationContext(new String[]{"parentbean.xml"});
        ApplicationContext factory = new ClassPathXmlApplicationContext(new String[]{"beanscopy4.xml"},pFactory);
        Boss boss = (Boss) factory.getBean("boss");
        Secretary secretary =  factory.getBean("secretary",Secretary.class);
        System.out.println(boss);
        System.out.println(secretary);
    }
}
