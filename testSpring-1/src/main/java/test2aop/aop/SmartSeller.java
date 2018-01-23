package test2aop.aop;

import org.springframework.stereotype.Component;
@Component
public class SmartSeller implements Seller{

    public void sell(String goods) {
        System.out.println("I am selling "+goods);
    }
}
