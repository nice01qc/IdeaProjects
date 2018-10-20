package one;

import aop.T;
import aop.TestBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.text.MessageFormat;
import java.util.*;

public class MyTestBean1 {

    @Test
    public void testSimpleLoad(){
//        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("one/beanFactoryTest.xml"));
//
//        MyTestBean myTestBean = (MyTestBean)bf.getBean("myTestBean");


        ApplicationContext apc = new ClassPathXmlApplicationContext("one/beanFactoryTest.xml");

        TestEvent event = new TestEvent("hello","msg");

        apc.publishEvent(event);
    }


    @Test
    public void testDate(){

        BeanFactory apc = new XmlBeanFactory(new ClassPathResource("one/beanfactory.xml"));

//        ClassPathXmlApplicationContext apc = new ClassPathXmlApplicationContext("one/aop.xml");
        T testBean = (T)apc.getBean("child");



        testBean.test();
    }
       @Test
    public void testPostProcessor(){

        ConfigurableListableBeanFactory apc = new XmlBeanFactory(new ClassPathResource("one/beanfactory.xml"));

        BeanFactoryPostProcessor bfpp = (BeanFactoryPostProcessor)apc.getBean("bfpp");

    }

    @Test
    public void testi18n(){
        String pattern1 = "{0}, 你好！你于{1}在工商银行存入{2}元";
        String pattern2 = "At {1,time,short}  On {1,date,long}, {0} paid {2,number,currency}.";

        Object[] params = {"John", new GregorianCalendar().getTime(), 1.0E3};

        String msg1 = MessageFormat.format(pattern1,params);

        MessageFormat mf = new MessageFormat(pattern2, Locale.US);
        String msg2 = mf.format(params);

        System.out.println(msg1);

        System.out.println(msg2);
    }

    @Test
    public void testss(){
        List<Integer> list = new ArrayList<Integer>();



    }





}
