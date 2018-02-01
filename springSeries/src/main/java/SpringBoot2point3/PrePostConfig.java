package SpringBoot2point3;

import org.springframework.context.annotation.Bean;

public class PrePostConfig {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    BeanWayService beanWayService(){
        return new BeanWayService();
    }

    @Bean
    JSP250WayService jsp250WayService(){
        return new JSP250WayService();
    }
}
