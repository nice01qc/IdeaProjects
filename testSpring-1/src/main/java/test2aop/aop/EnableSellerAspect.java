package test2aop.aop;


import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class EnableSellerAspect {


    /**
     * @Aspectj 切面引介增强，在此类里可以为那些类实现引介增强
     */
    @DeclareParents(value = "test2aop.aop.Waiter+",defaultImpl = SmartSeller.class)
    private Seller seller; //要实现的目标接口，也就是说value实现了此接口，可以转型为此类

    // @annotaion()函数-->这样就可以使用自己的注解进行注解了ps:NaughtyWaiter
    @AfterReturning("@annotation(test2aop.aop.annotation.NeedTest)")
    public void needTestFun(){
        System.out.println("needTestFun() executed !");
    }

    //以下是execution()函数：格式为
    //execution(<修饰符模式>? <返回类型模式> <方法名模式>(<参数模式>) <异常模式>?)   -->打问号可以省略

    /**
     * ps: execution(public * *(..)) :匹配所有目标类的public方法
     *     execution(* test2aop.Waiter.*(..)): 匹配Waiter接口的所有方法
     *     execution(* test2aop.Waiter+.*(..)) : 匹配Waiter接口及其所有实现类的方法
     *     方法参数ps: (String,*);(String,..);(Object+,*);其中“*”为一个，“..”为任意个
     *     一些模式：" .* " 表示包下所有类, " ..* " 表示包、子孙包下所有类
     */
    @Before("execution(public void test2aop.aop.NaiveWaiter.greetTo(String))")
    public void go(){
        System.out.println("goooooooooooooooooooooooooo");
    }

    /**
     * args()和@args() 函数：args是针对方法参数进行筛选，@args()针对方法参数进行注解 进行筛选-->没成功
     */
    @After("args(String)")
    public void call(){
        System.out.println("please call me ! ");
    }

    /**
     * within()函数,通过类来匹配，只匹配类及包，不匹配方法和入参,不会影响子类
     */
    @Before("within(test2aop.aop.*)")
    public void speak(){
        System.out.println("I am speaking !");
    }
    // 其它函数不介绍了

    /**
     * 切点函数复合使用&&,!,||
     */
    @Before("args(double,..)"+"||execution(* *Waiter(..))")
    public void all(){
        System.out.println("all begin ...");
    }

    //测试PointCut
    @Before("test2aop.aop.TestNamePointCut.inPkgGreetTo()")
    public void testPointCut(){
        System.out.println("Test pointCut...");
    }








}
