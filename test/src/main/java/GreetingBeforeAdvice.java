import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

@Aspect
public class GreetingBeforeAdvice implements MethodBeforeAdvice{

    public void before(Method method, Object[] objects, Object o) throws Throwable {

    }
}
