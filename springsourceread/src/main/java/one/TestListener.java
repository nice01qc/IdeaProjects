package one;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class TestListener implements ApplicationListener {
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof TestEvent){
            TestEvent testEvent = (TestEvent)event;
            testEvent.print();
        }
    }
}
