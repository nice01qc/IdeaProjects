package test2aop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 访问连接点信息
 */
@Component
@Aspect
public class GetPointInformation {
    @Around("execution(* test2aop.aop.NaughtyWaiter.*(..))")            //Around环绕增强需要加返回值，不然那些有返回值的方法会报错
    public Object joinPointAccess(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        Object[] args = pjp.getArgs();
        System.out.println("begein ......................."+args);
        try{
            result = pjp.proceed(args);
        }catch (Exception e){
            throw new Exception("不能除以0..");
        }

        System.out.println("end ......................"+pjp.getTarget().getClass().getName());
        return result;
    }

    //绑定连接点方法入参
    @Before("target(test2aop.aop.NaughtyWaiter) && args(x,y)")
    public void bindJoinPointparams(double x,double y){    //感觉需要知道有什么参数，才能进行绑定
        System.out.println("xxxxxxxxxxx:"+x +";yyyyyyyyyy: "+y);
    }

    //绑定代理对象
    @Before("execution(* *.get*(test2aop.aop.Waiter+,..)) && target(waiter)")
    public void bindProxyObj(Waiter waiter){
        System.out.println(waiter.getClass().getName()+"???????????????????");
    }

    //绑定返回值
    @AfterReturning(value = "target(Waiter+)",returning = "d")
    public void bindReturnValue(double d){
        System.out.println(d+"+++++++++++++++++");
    }
    @AfterThrowing(value = "target(Waiter+)",throwing = "e")
    public void bindException(Exception e){
        System.out.println(e.getMessage()+"................");
    }



}
