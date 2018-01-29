package springBootPassage1.exmaple1;

import org.springframework.stereotype.Component;

@Component
public class FunctionService {
    public String sayHello(String word){
        return "Hello " + word + " !";
    }
}
