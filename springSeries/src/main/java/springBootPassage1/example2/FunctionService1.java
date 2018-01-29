package springBootPassage1.example2;

import org.springframework.stereotype.Component;

@Component
public class FunctionService1 {
    public String sayHello(String word){
        return "Hello " + word + " !";
    }
}
