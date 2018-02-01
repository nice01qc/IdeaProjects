package springBoot2point5;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// 实现了ApplicationListener接口，并指定监听的事件类型，使用onApplicationEvent方法对消息进行接收处理

@Component
public class DemoListener implements ApplicationListener<DemoEvent> {

    public void onApplicationEvent(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("I listen your message :" + msg);
    }

}
