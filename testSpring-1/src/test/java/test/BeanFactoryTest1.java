package test;
/**
 * 这是关于bean加载的一些内容
 */

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;

public class BeanFactoryTest1 {
    public static void main(String[] args) throws IOException {
        /**
         * 通过XmlBeanFactory来启动Spring Ioc
         */
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource res = resolver.getResource("classpath:beans.xml");
//        BeanFactory bf = new XmlBeanFactory(res);
        /**
         * 如果文件在classpath下，优先使用这个加载
         */
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:beans.xml");
        /**
         * 如果文件在文件系统路径下，优先使用这个加载
         */
        ApplicationContext ctx = new FileSystemXmlApplicationContext(System.getProperty("user.dir")+"\\src\\main\\java\\test\\beans.xml");
        System.out.println("car initBeanFactory");
        Car1 car1 = ctx.getBean("car1",Car1.class);
        System.out.println(car1.getBrand());

        /**
         * 一下是获取文件路径的方式
         */
        System.out.println(System.getProperty("user.dir"));
        File directory = new File("src\\main\\java\\test\\beans.xml");//设定为当前文件夹
        try{
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        }catch(Exception e){}
    }
}
