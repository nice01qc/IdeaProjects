package springBoot2point4;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProfileConfigTest {

    AnnotationConfigApplicationContext context = null;

    @Test
    public void test1(){
        context = new AnnotationConfigApplicationContext();
        // 先设置
        context.getEnvironment().setActiveProfiles("dev");
//        context.getEnvironment().setActiveProfiles("prod");
        // 再注册
        context.register(ProfileConfig.class);
        // 刷新容器
        context.refresh();

        //测试结果
        DemoBean demoBean = (DemoBean) context.getBean("devDemoBean");  // Bean name为方法名

        System.out.println(demoBean.getContent());

        context.close();
    }
}
