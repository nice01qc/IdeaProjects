package springBootPassage1.example3;

import org.springframework.stereotype.Service;

@Service
public class DemoAnnotationService {

    @Action("注解式拦截类add")
    public void add(){
        System.out.println("this is add() method....");
    }
}
