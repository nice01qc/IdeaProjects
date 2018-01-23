package test2aop.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 用于存储需要复用的切点名字,在EnableSellerAspect最后有例子
 */

public class TestNamePointCut {
    @Pointcut("within(test2aop.aop.Waiter+)")
    private void inPackage(){}          //表明该切点只能在本类中使用

    @Pointcut("within(test2aop.aop.Waiter+)")
    protected  void greetTo(){}         //表明该切点只能在本包中使用

    @Pointcut("within(test2aop.aop.Waiter+)")
    public void inPkgGreetTo(){}        //表明该切点随意使用
}
