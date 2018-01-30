package springaop;

import org.springframework.stereotype.Component;

@Component
public class NaiveWaiter implements Waiter {

    public void greetTo(String name) {
        System.out.println("greet to " + name + "....");
    }


    public double div(double x,double y){
        System.out.println(x/y);
        return x/y;
    }


    public void getWaiter(){
        System.out.println("I get a waiter !");
    }
}