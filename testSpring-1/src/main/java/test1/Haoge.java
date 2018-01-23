package test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Scope("singleton")
@Controller
public class Haoge {
    /**
     * 一下方法自己随意组合就好
     */
    @Qualifier("car")               //有多过bean名字时，可以用此来区分
    @Autowired(required = false)   // required is false 没有car也可以完成装配，不报错
    private Car car;

    @Autowired(required = false)  //效果同上
    public void setCar(@Qualifier("car") Car car) {        //也可以对参数进行标记
        this.car = car;
    }

    public Car getCar() {
        return car;
    }


    @Override
    public String toString() {
        return "Haoge{" +
                "car=" + car +
                '}';
    }


//    一下是@PostConstruct , @PreDestroy 注解，在spring中相当于init-method,destroy-method
    @PostConstruct
    private void init(){                    //在初始化此bean时调用
        car.setName("aodi");
        System.out.println("init1......");
    }
    @PreDestroy
    private void destory(){                 //在销毁此bean时调用
        System.out.println("destory1.....");
    }

}
