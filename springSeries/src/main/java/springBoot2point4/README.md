## 	Profile

Profile 为在不同环境下使用不同的配置提供了支持，通过注解来切换

- [x] 通过设定Environment的ActiveProfiles 来设定当前context需要使用的配置环境。在开发中使用@Profile注解或者方法，达到在不同的环境下选择实例不同的Bean


- [ ] 通过设定jvm的spring.profiles.active 参数来设置配置环境

- [x] Web项目设置在Servlet的context parameter 中。

      ```
      <!-- servlet2.5一下 -->
      <servlet>
      	<servlet-name>dispatcher</servlet-name>
      	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      	<init-param>
      		<param-name>spring.profiles.active</param-name>
      		<param-name>production</param-value>
      	</init-param>
      </servlet>		
      ```

      ```
      <!-- servlet3.0以上 -->
      public class WebInit implements WebApplicationInitializer{
        @Override
        public void onStartup(ServletContext )
      }
      ```
## 其测试目录下包含 spring-test 测试部分知识
1. SpringJUnit4ClassRunner在JUnit环境下提供Spring TestContextFrameWork的功能。
2. @ContextConfiguration 用来加载配置ApplicationContext，其中classes属性用来加载配置类。
3. @ActiveProfiles 用来声明活动得到profile。
4. 可使用普通的@Autowired注入Bean。
5. 测试代码，通过JUnit的Assert来效验结果是否与预期一致。

