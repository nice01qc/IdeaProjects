package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 通过要给Java类 来代替xml配置，也就是说到时直接加载这个类就好了，然后会把东西加载好
 */
//表示一个配置信息的提供类
@Configuration
@ImportResource("classpath:beans.xml")   //如果此路径中有id与@Bean中定义的name相同，则@bean会被覆盖
public class Beans2 {
    //定义一个Bean
    @Bean(name = "car111")
    public Car1 BuildCar(){
        Car1 car1 = new Car1();
        car1.setBrand("222222222222222");
        car1.setMaxSpeed("200");
        return car1;
    }
    @Bean(name = "car222")
    public Car1 BuildCar1(){
        Car1 car1 = new Car1();
        car1.setBrand("ao22222222222222222");
        car1.setMaxSpeed("400");
        return car1;
    }
}
