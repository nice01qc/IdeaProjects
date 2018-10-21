package autowire;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    @org.junit.Test
    public void test1(){
        // 实例化 Spring IoC 容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:autowire/auto.xml");

        // 从容器中获得 MessagePrinter 的实例
        MessagePrinter printer = context.getBean(MessagePrinter.class);

        // 使用对象
        if (printer != null) printer.printMessage();
    }
}
