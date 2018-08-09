import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class LoginLogoutTest {

    @Test
    public void testHelloWorld(){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");

        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

        try{
            // 登陆验证
            subject.login(token);
            System.out.println("ok");
        }catch (AuthenticationException e){
            System.out.println("登陆验证失败");
        }finally {

        }


        Assert.assertEquals(true,subject.isAuthenticated());


        System.out.println("I will logout");

        // 退出
        subject.logout();;




    }


    @Test
    public void testAllSuccessfulStrategyWithSuccess() {
        String configFile = "classpath:shiro-authenticator-all-success.ini";
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
                new IniSecurityManagerFactory(configFile);
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang@163.com", "123");
        subject.login(token);

        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        System.out.println(principalCollection.asList());
    }

    private void login(String configFile) {

    }

}
