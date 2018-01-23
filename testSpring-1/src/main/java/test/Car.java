package test;

import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public interface Car extends InstantiationAwareBeanPostProcessor, BeanFactoryAware,BeanNameAware,InitializingBean,DisposableBean{
    public String toString();

}
