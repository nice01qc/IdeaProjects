package springBootPassage1.example2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UseFunctionService1 {

    @Autowired
    FunctionService1 functionService1;

    public void setFunctionService1(FunctionService1 functionService1) {
        this.functionService1 = functionService1;
    }

    public FunctionService1 getFunctionService1() {
        return functionService1;
    }

    public String SayHello(String word){
        return functionService1.sayHello(word);
    }
}
