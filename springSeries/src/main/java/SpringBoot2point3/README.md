## Bean 的初始化和销毁

##### Spring 对Bean 的生命周期提供了支持。有两种方式：

1. Java配置方式：使用@Bean的 initMethod 和 destroyMethod(相当于xml配置的init-method 和 destroZZy-method)。
2. 注解方式：使用JSP-250的@PostConstruct 和 @PreDestroy。