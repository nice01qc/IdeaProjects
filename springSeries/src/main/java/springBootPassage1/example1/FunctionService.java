package springBootPassage1.example1;

import org.springframework.stereotype.Component;

@Component
public class FunctionService {
    public String sayHello(String word){
        return "Hello " + word + " !";
    }
}
