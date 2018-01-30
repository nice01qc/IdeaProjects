package springaop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EnableWaiterAspect {
    /**
     * ps: execution(public * *(..)) :匹配所有目标类的public方法
     *     execution(* test2aop.Waiter.*(..)): 匹配Waiter接口的所有方法
     *     execution(* test2aop.Waiter+.*(..)) : 匹配Waiter接口及其所有实现类的方法
     *     方法参数ps: (String,*);(String,..);(Object+,*);其中“*”为一个，“..”为任意个
     *     一些模式：" .* " 表示包下所有类, " ..* " 表示包、子孙包下所有类
     */


    @Before("execution(public void springaop.NaiveWaiter.greetTo(String))")
    public void befor(){
        System.out.println("this is before .....");
    }

    @After("args(String)")
    public void call(){
        System.out.println("please call me ! ");
    }

}
