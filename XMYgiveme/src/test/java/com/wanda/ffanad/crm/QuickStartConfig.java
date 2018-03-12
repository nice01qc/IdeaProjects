package com.wanda.ffanad.crm;

import com.wanda.ffanad.crm.interceptors.LoginInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuickStartConfig extends WebMvcConfigurerAdapter implements BeanFactoryPostProcessor {
    private static String [] needLoadClassNames = {
    };

    private static Set<String> classNameSet = new HashSet<>();
    static {
        for (String name : needLoadClassNames) classNameSet.add(name);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;

        for(String beanName : listableBeanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            beanDefinition.setLazyInit(!classNameSet.contains(beanName));
        }
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new ApiPrintInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}