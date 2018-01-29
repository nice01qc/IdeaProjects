package springxmlconfig.pojo1;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springxmlconfig.pojo.Boy;

public class TestPojo1 {

     ApplicationContext ctx = null;

     @Test
     public void testpojo(){
         ctx = new ClassPathXmlApplicationContext("springannotationconfig.xml");

         Car car = (Car)ctx.getBean("car1");
         car.carsay();

         Boy boy = (Boy)ctx.getBean("boy1");
         boy.boysay();


     }







}
