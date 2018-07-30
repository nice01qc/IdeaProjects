package test2aop.aop;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test2aop.aop.annotation.NeedTest;

@Component
public class NaughtyWaiter implements Waiter {

    @NeedTest
    public void greetTo(String name) {
        System.out.println("girl "+name);
    }

    public void serveTo(String name) {
        System.out.println("girl "+name);
    }

    @NeedTest
    public double div( double x,double y){
        System.out.println(x/y);
        return x/y;
    }

    public void getWaiter(Waiter waiter){
        System.out.println("I get a waiter !");
    }
}
