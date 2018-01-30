package springBootPassage1.example3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("springBootPassage1.example3")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopConfig {

}
