package springBootPassage1.example2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


// 配置类
@Configuration
public class JavaConfig {

    @Bean
//    @Scope("prototype")
    public FunctionService1 functionService(){
        return new FunctionService1();
    }

    @Bean
    @Scope("prototype")
    public UseFunctionService1 useFunctionService(){
        UseFunctionService1 useFunctionService1 = new UseFunctionService1();
        useFunctionService1.setFunctionService1(functionService());
        return useFunctionService1;
    }

    @Bean
    @Scope("prototype")
    public UseFunctionService1 useFunctionService(FunctionService1 functionService1){
        UseFunctionService1 useFunctionService1 = new UseFunctionService1();
        useFunctionService1.setFunctionService1(functionService1);
        return useFunctionService1;
    }
}
