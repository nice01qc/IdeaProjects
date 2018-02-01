package SpringBoot2point3;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class JSP250WayService {

    @PostConstruct
    public void init(){
        System.out.println("jsr250-init-method");
    }

    public JSP250WayService(){
        super();
        System.out.println("构造函数初始化-JSR250WayService");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("jsr250-destroy-method");
    }
}
