package springBoot2point5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

// 事件发布

@Component
public class DemoPublisher {

    @Autowired
    ApplicationContext applicationContext;

    public void publish(String msg){
        applicationContext.publishEvent(new DemoEvent(this,msg));
    }
}
