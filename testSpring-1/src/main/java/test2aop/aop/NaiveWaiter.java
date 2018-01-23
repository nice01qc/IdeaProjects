package test2aop.aop;

import org.springframework.stereotype.Component;

@Component
public class NaiveWaiter implements Waiter {
    public void greetTo(String name) {
        System.out.println("greet to " + name + "....");
    }

    public void serveTo(String name) {
        System.out.println("serving "+ name + "...");
    }
    public double div(double x,double y){
        System.out.println(x/y);
        return x/y;
    }
    public void getWaiter(Waiter waiter){
        System.out.println("I get a waiter !");
    }
}
