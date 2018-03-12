package com.wanda.ffanad.crm;

import java.util.Properties;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.HttpPutFormContentFilter;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

/**
 * 程序的入口类. 定义了一系列Bean.
 */
@EnableTransactionManagement
@EnableAutoConfiguration
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan("com.wanda.ffanad")
@MapperScan(basePackages = {"com.wanda.ffanad.core.mappers","com.wanda.ffanad.base.dal.mappers","com.wanda.ffanad.crm.mappers"})
@ServletComponentScan("com.wanda.ffanad")
public class CRMApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    
    //delete by xuyao16, 暂时移除读写分离逻辑
	//@Autowired
	//private DataSource dataSource;
    
    /**
     * 生成War需要
     * 
     * @param application
     *            SpringApplicationBuilder
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CRMApplication.class);
    }

    /**
     * 程序的入口方法.
     *
     * @param args
     *            程序运行的各种参数
     */
    public static void main(final String[] args) {
        SpringApplication.run(CRMApplication.class, args);
    }
    
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 从datasource创建SqlSessionFactory.扫描mybatis里所有xml文件，配置SqlSessionFactory.
     *
     * @return SqlSessionFactory
     * @throws Exception
     *             getObject时有机会抛出异常，另外扫描xml文件也会有IO异常抛出
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.put("dialect", "mysql");
        properties.put("reasonable", "true");
        properties.put("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        Interceptor[] interceptors = new Interceptor[] { pageHelper };
        sqlSessionFactoryBean.setPlugins(interceptors);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mybatis/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "adapterConfig")
    public PropertiesFactoryBean loadProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource resource = new ClassPathResource("ffanad_common_adapter.properties");
        propertiesFactoryBean.setLocations(resource);
        return propertiesFactoryBean;
    }

    /**
     * 从datasource创建TransactionManager.
     *
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * 
     * @author 姜涛
     * @return
     */
    @Bean
    public FilterRegistrationBean myFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new HttpPutFormContentFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
