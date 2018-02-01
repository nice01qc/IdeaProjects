package springBoot2point4;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProfileConfig.class})
@ActiveProfiles("prod")
public class SpringTestExample {

    @Autowired
    private DemoBean demoBean;

    @Test
    public void prodBeanShouldInject(){
        String expected = "from production profile ";
        String actual = demoBean.getContent();

//        System.out.println(expected+":"+actual);
        Assert.assertEquals(expected,actual);

    }
}
